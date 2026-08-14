package com.techfinder.localserviceprovider.presentation.screens.registrationscreen

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.techfinder.localserviceprovider.R
import com.techfinder.localserviceprovider.presentation.viewmodel.AuthState
import com.techfinder.localserviceprovider.presentation.viewmodel.PhoneAuthViewModel
import com.techfinder.localserviceprovider.ui.theme.darkBlue

@Composable
fun LoginScreen(
    onOtpSent: (String) -> Unit,
    viewModel: PhoneAuthViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val activity = context as? Activity

    val authState by viewModel.authState.collectAsStateWithLifecycle()

    var countryCode by remember { mutableStateOf("+91") }
    var phoneNumber by remember { mutableStateOf("") }

    LaunchedEffect(authState) {
        when (val state = authState) {

            is AuthState.CodeSent -> {
                val fullPhone = "$countryCode${phoneNumber.trim()}"
                onOtpSent(fullPhone)
                viewModel.resetState()
            }

            is AuthState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                viewModel.resetState()
            }

            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(80.dp))

        Text(
            text = "Enter your phone number",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = darkBlue
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

            OutlinedTextField(
                value = countryCode,
                onValueChange = { countryCode = it },
                modifier = Modifier.width(90.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = darkBlue,
                    unfocusedBorderColor = darkBlue.copy(alpha = 0.6f)
                )
            )

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it.filter { ch -> ch.isDigit() } },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Phone Number") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = darkBlue,
                    unfocusedBorderColor = darkBlue.copy(alpha = 0.6f)
                )
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        if (authState is AuthState.Loading) {
            CircularProgressIndicator(color = darkBlue)
        } else {
            Button(
                onClick = {
                    val fullPhone = "$countryCode${phoneNumber.trim()}"
                    activity?.let {
                        viewModel.sendOtp(it, fullPhone)
                    }
                },
                enabled = phoneNumber.trim().length == 10,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = darkBlue)
            ) {
                Text(
                    text = "Send OTP",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        Text(text = "Or continue with")

        Spacer(modifier = Modifier.height(12.dp))

        AuthOption(image = R.drawable.google)
    }
}

@Composable
fun AuthOption(
    modifier: Modifier = Modifier,
    image: Int,
    tint: Color? = null,
    contentDescription: String? = null
){

    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(0.2f),
                shape = RoundedCornerShape(14.dp)
            )
            .clip(RoundedCornerShape(14.dp))
            .clickable{}
            .padding(horizontal = 35.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {

        if (tint != null){
            Icon(
                painter = painterResource(image),
                contentDescription = contentDescription,
                tint = tint,
                modifier = Modifier.size(30.dp)
            )
        }else{
            Image(
                painter = painterResource(image),
                contentDescription = contentDescription,
                modifier = Modifier.size(30.dp)
            )

        }
    }
}