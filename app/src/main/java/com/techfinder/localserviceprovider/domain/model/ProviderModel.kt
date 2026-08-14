package com.techfinder.localserviceprovider.domain.model

data class ProviderModel(
    val uid: String ="",
    val name : String ="",
    val profileImage: String ="",
    val phone : String ="",
    val category: String ="",
    val description: String ="",
    val isAvailable: Boolean = true,
    val experienceYears: Int = 0,
    val address: String ="",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val rating: Double = 0.0,
    val totalReviews: Int = 0
)