package com.techfinder.localserviceprovider.presentation.viewmodel

import android.app.Activity
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techfinder.localserviceprovider.domain.model.PhoneAuthUser
import com.techfinder.localserviceprovider.domain.usecases.SendOtpUseCase
import com.techfinder.localserviceprovider.domain.usecases.VerifyOtpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhoneAuthViewModel @Inject constructor(
    private val sendOtpUseCase: SendOtpUseCase,
    private val verifyOtpUseCase: VerifyOtpUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    private var verificationId: String?
        get() = savedStateHandle["verification_id"]
        set(value) { savedStateHandle["verification_id"] = value}

    fun sendOtp(activity: Activity, phoneNumber: String){
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            sendOtpUseCase(
                activity = activity,
                phone = phoneNumber,
                onCodeSent = { id ->
                    verificationId = id
                    _authState.value = AuthState.CodeSent(id)
                },
                onError = { message ->
                    _authState.value = AuthState.Error(message)
                }
            )
        }
    }

    fun verifyOtp(otp: String, phoneNumber: String, role: String){
        val currentVerificationId = verificationId ?: run {
            _authState.value = AuthState.Error("Verification session expired. Please resend OTP")
            return
        }

        _authState.value = AuthState.Loading
        viewModelScope.launch {
            val user = PhoneAuthUser(
                phone = phoneNumber,
                role = role
            )

            verifyOtpUseCase(
                verificationId = currentVerificationId,
                otp = otp,
                user = user,
                onSuccess = {
                    _authState.value = AuthState.Success(user)
                },
                onError = { message ->
                    _authState.value = AuthState.Error(message)
                }
            )
        }
    }

    fun resetState(){
        _authState.value = AuthState.Idle
    }

}


sealed class AuthState{

    object Idle: AuthState()
    object Loading: AuthState()
    data class CodeSent(val verificationId: String): AuthState()
    data class Success(val user: PhoneAuthUser): AuthState()
    data class Error(val message: String): AuthState()
}