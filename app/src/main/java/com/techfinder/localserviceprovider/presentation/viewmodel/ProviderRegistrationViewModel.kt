package com.techfinder.localserviceprovider.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techfinder.localserviceprovider.domain.model.ProviderModel
import com.techfinder.localserviceprovider.domain.usecases.providerusecase.RegisterProviderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProviderRegistrationViewModel @Inject constructor(
    private val registerProviderUseCase: RegisterProviderUseCase
): ViewModel() {

    private val _registrationState = MutableStateFlow<ProviderRegistrationState>(
        ProviderRegistrationState.Idle
    )

    val registrationState = _registrationState.asStateFlow()

    fun registerProvider(
        provider: ProviderModel
    ){

        _registrationState.value =
            ProviderRegistrationState.Loading

        viewModelScope.launch {

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
        }
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