package com.techfinder.localserviceprovider.domain.usecases

import android.app.Activity
import com.techfinder.localserviceprovider.domain.repository.AuthRepository
import javax.inject.Inject

class SendOtpUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        activity: Activity,
        phone: String,
        onCodeSent: (verificationId: String) -> Unit,
        onError: (String) -> Unit
    ){
        repository.sendOtp(activity,phone, onCodeSent, onError )
    }
}