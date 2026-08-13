package com.techfinder.localserviceprovider.presentation.welcomescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Preview
@Composable
fun WelcomeScreen(){

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Text(
            text = "Welcome",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Login",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(400.dp))

        TextButton(
            onClick = {},

            modifier = Modifier
                .size(height = 54.dp, width = 160.dp)
                .fillMaxWidth()
                .background(color = Color.Black)
                .clip(shape = RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "Continue",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}