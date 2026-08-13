package com.techfinder.localserviceprovider.domain.model

data class PhoneAuthUser(
    val uid: String = "",
    val name: String = "",
    val phone: String = "",
    val role: String = "customer",
    val providerProfileCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
