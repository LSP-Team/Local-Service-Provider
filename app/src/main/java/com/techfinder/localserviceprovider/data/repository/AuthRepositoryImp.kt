package com.techfinder.localserviceprovider.data.repository

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.techfinder.localserviceprovider.domain.model.PhoneAuthUser
import com.techfinder.localserviceprovider.domain.repository.AuthRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor(
    private val auth : FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository{

   // private var verificationId: String =""

    override suspend fun sendOtp(
        activity: Activity,
        phone: String,
        onCodeSent: (verificationId: String) -> Unit,
        onError: (String) -> Unit
    ) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                }

                override fun onVerificationFailed(e: FirebaseException) {
                    onError(e.message ?: "OTP failed")
                }

                override fun onCodeSent(
                    id: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    onCodeSent(id)
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override suspend fun verifyOtp(
        verificationId: String,
        otp: String,
        user: PhoneAuthUser,
        onSuccess: (PhoneAuthUser) -> Unit,
        onError: (String) -> Unit
    ) {
        val credential = PhoneAuthProvider.getCredential(verificationId, otp)

        auth.signInWithCredential(credential)
            .addOnSuccessListener { result ->

                val uid = result.user?.uid
                if (uid == null) {
                    onError("Authentication failed: User not found")
                    return@addOnSuccessListener
                }

               // val updatedUser = user.copy(uid = uid)
                val updatedUser = user.copy(
                    uid = uid,
                    phone = result.user?.phoneNumber ?: user.phone
                )

                firestore.collection("users")
                    .document(uid)
                    .set(updatedUser)
                    .addOnSuccessListener { onSuccess(updatedUser) }
                    .addOnFailureListener { onError(it.message ?: "Save failed") }
            }
            .addOnFailureListener {
                onError(it.message ?: "Invalid OTP")
            }
    }

    override fun isUserLoggedIn(): Boolean {
        return  auth.currentUser != null
    }

    override fun logout() {
        auth.signOut()
    }
}