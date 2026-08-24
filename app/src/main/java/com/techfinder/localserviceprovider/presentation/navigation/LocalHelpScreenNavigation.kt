package com.techfinder.localserviceprovider.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.techfinder.localserviceprovider.presentation.screens.homescreen.HomeScreen
import com.techfinder.localserviceprovider.presentation.screens.registrationscreen.loginscreen.LoginScreen
import com.techfinder.localserviceprovider.presentation.screens.registrationscreen.loginscreen.OtpScreen
import com.techfinder.localserviceprovider.presentation.screens.registrationscreen.loginscreen.WelcomeScreen
import com.techfinder.localserviceprovider.presentation.screens.registrationscreen.providerscreen.ProviderBasicInfoScreen
import com.techfinder.localserviceprovider.presentation.screens.registrationscreen.providerscreen.ProviderPhotoAndLocationScreen
import com.techfinder.localserviceprovider.presentation.screens.splashscreen.SplashScreen
import com.techfinder.localserviceprovider.presentation.viewmodel.PhoneAuthViewModel
import com.techfinder.localserviceprovider.presentation.viewmodel.ProviderRegistrationViewModel

@Composable
fun LocalHelpScreenNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SplashScreen
    ) {

        // Splash
        composable<Routes.SplashScreen> {
            SplashScreen(navHostController = navController)
        }

        // Login
        composable<Routes.LoginScreen> { backStackEntry ->

            val authViewModel: PhoneAuthViewModel =
                hiltViewModel(backStackEntry)
            LoginScreen(
                navHostController = navController,
                viewModel = authViewModel,
                onOtpSent = { phoneNumber ->
                    navController.navigate(
                        Routes.OtpScreen(
                            phoneNumber = phoneNumber
                        )
                    )
                }
            )
        }

        // OTP
        composable<Routes.OtpScreen> { backStackEntry ->
            val route = backStackEntry.toRoute<Routes.OtpScreen>()

            val loginEntry = remember(backStackEntry) {
                navController.getBackStackEntry(
                    Routes.LoginScreen
                )
            }

            val authViewModel: PhoneAuthViewModel = hiltViewModel(loginEntry)
            OtpScreen(
                phone = route.phoneNumber,
                navHostController = navController,
                viewModel = authViewModel,
                onAuthSuccess = {
                    navController.navigate(Routes.WelcomeScreen) {
                        popUpTo(Routes.LoginScreen) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Welcome / Role Selection
        composable<Routes.WelcomeScreen> {
            WelcomeScreen(
                navHostController = navController
            )
        }

        // Provider Basic Information (Step 1)
        composable<Routes.ProviderBasicInfoScreen> { backStackEntry ->
            val sharedViewModel: ProviderRegistrationViewModel = hiltViewModel(backStackEntry)
            ProviderBasicInfoScreen(
                viewModel = sharedViewModel,
                onNext = {
                    navController.navigate(Routes.ProviderPhotoAndLocationScreen)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // Provider Photo + Location (Step 2)
        composable<Routes.ProviderPhotoAndLocationScreen> { backStackEntry ->
            // Re-use ViewModel instance from Step 1 so data is retained across screens
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.ProviderBasicInfoScreen)
            }
            val sharedViewModel: ProviderRegistrationViewModel = hiltViewModel(parentEntry)

            ProviderPhotoAndLocationScreen(
                viewModel = sharedViewModel,
                onBack = {
                    navController.popBackStack()
                },
                onRegistrationSuccess = {
                    navController.navigate(Routes.HomeScreenScreen) {
                        popUpTo(Routes.WelcomeScreen) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Home Screen
        composable<Routes.HomeScreenScreen> {
            HomeScreen(
                navHostController = navController
            )
        }
    }
}