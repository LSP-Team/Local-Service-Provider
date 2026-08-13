package com.techfinder.localserviceprovider.domain.usecases

import com.techfinder.localserviceprovider.domain.model.PhoneAuthUser
import com.techfinder.localserviceprovider.domain.repository.AuthRepository
import javax.inject.Inject

class VerifyOtpUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        verificationId: String,
        otp: String,
        user: PhoneAuthUser,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ){
        repository.verifyOtp(verificationId, otp, user, onSuccess, onError)
    }
}