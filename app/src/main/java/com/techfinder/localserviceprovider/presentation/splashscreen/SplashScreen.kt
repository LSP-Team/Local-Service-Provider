package com.techfinder.localserviceprovider.presentation.splashscreen

import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.techfinder.localserviceprovider.R

@Preview(showSystemUi = true)
@Composable
fun SplashScreen(){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Image(painter = painterResource(id = R.drawable.serviceapp_logo),
            contentDescription = null,
        modifier = Modifier.size(280.dp)
           .align(alignment = Alignment.Center)
            )
    }
}