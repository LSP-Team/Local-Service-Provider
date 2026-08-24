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
import com.techfinder.localserviceprovider.presentation.navigation.LocalHelpScreenNavigation
import com.techfinder.localserviceprovider.presentation.screens.homescreen.HomeScreen
import dagger.hilt.android.AndroidEntryPoint
import com.techfinder.localserviceprovider.presentation.screens.registrationscreen.loginscreen.LoginScreen
import com.techfinder.localserviceprovider.presentation.screens.registrationscreen.loginscreen.OtpScreen
import com.techfinder.localserviceprovider.ui.theme.LocalServiceProviderTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LocalServiceProviderTheme {

                LocalHelpScreenNavigation()
            }
        }
    }
}

