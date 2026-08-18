package com.techfinder.localserviceprovider.domain.usecases.authusecase

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
        onSuccess: (PhoneAuthUser) -> Unit,
        onError: (String) -> Unit
    ){
        repository.verifyOtp(
            verificationId = verificationId,
            otp = otp,
            user = user,
            onSuccess = onSuccess,
            onError = onError
         )
    }
}