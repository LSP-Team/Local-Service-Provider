package com.techfinder.localserviceprovider.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.techfinder.localserviceprovider.domain.model.ProviderModel
import com.techfinder.localserviceprovider.domain.usecases.providerusecase.RegisterProviderUseCase
import com.techfinder.localserviceprovider.domain.usecases.providerusecase.UploadProviderProfileImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProviderRegistrationViewModel @Inject constructor(
    private val registerProviderUseCase: RegisterProviderUseCase,
    private val uploadProviderProfileImageUseCase: UploadProviderProfileImageUseCase,
    private val firebaseAuth: FirebaseAuth
): ViewModel() {

    // temporary reg. data
    private var registrationData = ProviderRegistrationData()
    private val _registrationState = MutableStateFlow<ProviderRegistrationState>(
        ProviderRegistrationState.Idle
    )

    val registrationState = _registrationState.asStateFlow()

    fun getAuthenticatedPhone(): String {
        return firebaseAuth.currentUser?.phoneNumber ?: ""
    }

    // step 1 (basic info)
    fun saveBasicInfo(
        name: String,
        category: String,
        experienceYears: Int,
        description: String
    ){
        registrationData = registrationData.copy(
            name = name,
            phone = firebaseAuth.currentUser?.phoneNumber ?: "",
            category = category,
            experienceYears = experienceYears,
            description = description
        )
    }

    // step 2
    fun saveProfileAndLocation(
        profileImage: String,
        address: String,
        latitude: Double,
        longitude: Double
    ){
        registrationData = registrationData.copy(
            profileImage = profileImage,
            address = address,
            latitude = latitude,
            longitude = longitude
        )
    }
    fun registerProvider(){

        val currentUser = firebaseAuth.currentUser

        if (currentUser == null){
            _registrationState.value =
                ProviderRegistrationState.Error(
                    "User is not authenticated"
                )

            return
        }

        if (registrationData.profileImage.isBlank()){
            _registrationState.value =
                ProviderRegistrationState.Error(
                    "Profile image is required"
                )
            return
        }

        if (registrationData.address.isBlank()){
            _registrationState.value = ProviderRegistrationState.Error(
                "Service address is required"
            )
            return
        }

        if (registrationData.latitude == 0.0 || registrationData.longitude == 0.0){
            _registrationState.value = ProviderRegistrationState.Error(
                "Service location is required"
            )
            return
        }


        _registrationState.value =
            ProviderRegistrationState.Loading

        viewModelScope.launch {

            try {
                val imageUri = Uri.parse(registrationData.profileImage)

                val uploadResult =
                    uploadProviderProfileImageUseCase(
                        uid = currentUser.uid,
                        imageUri = imageUri
                    )

                val imageUrl = uploadResult.getOrElse { exception ->
                    _registrationState.value = ProviderRegistrationState.Error(
                        exception.message
                            ?: "Failed to upload profile image"
                    )

                    return@launch
                }

                val provider = ProviderModel(
                    uid = currentUser.uid,
                    name = registrationData.name,
                    phone = registrationData.phone,
                    profileImage = imageUrl,
                    category = registrationData.category,
                    customCategory = registrationData.customCategory,
                    description = registrationData.description,
                    experienceYears = registrationData.experienceYears,
                    isAvailable = true,
                    address = registrationData.address,
                    latitude = registrationData.latitude,
                    longitude = registrationData.longitude,
                    rating = 0.0,
                    totalReviews = 0
                )
                val result = registerProviderUseCase(provider)

                result.onSuccess {
                    _registrationState.value = ProviderRegistrationState.Success
                }
                    .onFailure { exception ->
                        _registrationState.value = ProviderRegistrationState.Error(
                            exception.message
                                ?: "Registration Failed"
                        )
                    }
            } catch (e: Exception){
                _registrationState.value = ProviderRegistrationState.Error(
                    e.message
                        ?: "Something went wrong"
                )
            }
        }
    }


    fun getRegistrationData(): ProviderRegistrationData{
        return registrationData
    }

    fun resetRegistration(){

        registrationData =
            ProviderRegistrationData()

        _registrationState.value =
            ProviderRegistrationState.Idle
    }

    fun resetState(){
        _registrationState.value = ProviderRegistrationState.Idle
    }
}

sealed class ProviderRegistrationState{

    object Idle: ProviderRegistrationState()

    object Loading: ProviderRegistrationState()

    object Success: ProviderRegistrationState()

    data class Error(
        val message: String
    ) : ProviderRegistrationState()
}


data class ProviderRegistrationData(
    val name: String = "",
    val phone: String = "",

    val category: String = "",
    val customCategory: String = "",

    val description: String = "",
    val experienceYears: Int = 0,

    val profileImage: String = "",

    val address: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)
