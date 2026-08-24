package com.techfinder.localserviceprovider.presentation.screens.registrationscreen.loginscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.techfinder.localserviceprovider.presentation.navigation.Routes

@Composable
fun WelcomeScreen(
    navHostController: NavHostController
){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Button(
            onClick = {
                navHostController.navigate(Routes.HomeScreenScreen)
            }
        ) {
            Text(
                text = "Customer"
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                navHostController.navigate(Routes.ProviderBasicInfoScreen)
            }
        ) {
            Text(
                text = "Provider"
            )
        }
    }
}