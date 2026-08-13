package com.techfinder.localserviceprovider

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.techfinder.localserviceprovider.presentation.homescreen.HomeScreen
import dagger.hilt.android.AndroidEntryPoint
import com.techfinder.localserviceprovider.presentation.registrationscreen.LoginScreen
import com.techfinder.localserviceprovider.presentation.registrationscreen.OtpScreen
import com.techfinder.localserviceprovider.ui.theme.LocalServiceProviderTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LocalServiceProviderTheme {

                // temporary navigation
                var currentScreen by remember { mutableStateOf("login") }
                var phone by remember { mutableStateOf("") }

                when (currentScreen) {

                    "login" -> {
                        LoginScreen(
                            onOtpSent = { fullPhone ->
                                phone = fullPhone
                                currentScreen = "otp"
                            }
                        )
                    }

                    "otp" -> {
                        OtpScreen(
                            phone = phone,
                            role = "customer",
                            onAuthSuccess = { role ->

                                Toast.makeText(
                                    this@MainActivity,
                                    "Logged in as $role",
                                    Toast.LENGTH_LONG
                                ).show()

                                // navigate to your real HomeScreen
                                currentScreen = "home"
                            }
                        )
                    }

                    "home" -> {
                        HomeScreen()
                    }
                }
            }
        }
    }
}

