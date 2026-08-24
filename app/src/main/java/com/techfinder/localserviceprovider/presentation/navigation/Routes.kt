package com.techfinder.localserviceprovider.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    data object SplashScreen: Routes()
    @Serializable
    data object LoginScreen: Routes()
    @Serializable
    data class OtpScreen(
        val phoneNumber: String
    ): Routes()
    @Serializable
    data object WelcomeScreen: Routes()
    @Serializable
    data object ProviderBasicInfoScreen: Routes()
    @Serializable
    data object ProviderPhotoAndLocationScreen: Routes()
    @Serializable
    data object HomeScreenScreen: Routes()

}