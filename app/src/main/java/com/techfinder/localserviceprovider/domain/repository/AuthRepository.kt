package com.techfinder.localserviceprovider.domain.repository

import android.app.Activity
import com.techfinder.localserviceprovider.domain.model.PhoneAuthUser

interface AuthRepository {

    suspend fun sendOtp(
        activity: Activity,
        phone: String,
        onCodeSent: (verificationId: String) -> Unit,
        onError: (String) -> Unit
    )

    suspend fun verifyOtp(
        verificationId: String,
        otp: String,
        user: PhoneAuthUser,
        onSuccess: (PhoneAuthUser) -> Unit,
        onError: (String) -> Unit
    )

    fun isUserLoggedIn(): Boolean

    fun logout()
}
