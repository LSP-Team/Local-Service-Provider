package com.techfinder.localserviceprovider.domain.model

data class ProviderModel(

    val uid: String ="",
    val name : String ="",
    val phone : String ="",

    val profileImage: String ="",

    val category: String ="",
    val customCategory: String ="",

    val description: String ="",
    val experienceYears: Int = 0,

    val isAvailable: Boolean = true,

    val address: String ="",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,

    val rating: Double = 0.0,
    val totalReviews: Int = 0
)