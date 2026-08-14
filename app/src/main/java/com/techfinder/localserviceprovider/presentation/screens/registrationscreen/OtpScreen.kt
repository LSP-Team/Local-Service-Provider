package com.techfinder.localserviceprovider.presentation.screens.registrationscreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.techfinder.localserviceprovider.presentation.viewmodel.AuthState
import com.techfinder.localserviceprovider.presentation.viewmodel.PhoneAuthViewModel
import com.techfinder.localserviceprovider.ui.theme.darkBlue
import com.techfinder.localserviceprovider.ui.theme.orange

@Composable
fun OtpScreen(
    phone: String,
    role: String = "customer",
    onAuthSuccess: (String) -> Unit,
    viewModel: PhoneAuthViewModel = hiltViewModel()
){

    val context = LocalContext.current
    var otp by remember { mutableStateOf("") }

    val authState by viewModel.authState.collectAsStateWithLifecycle()

    LaunchedEffect(authState) {
        when (val state = authState){
            is AuthState.Success ->{
               onAuthSuccess(state.user.role)
               viewModel.resetState()
            }

            is AuthState.Error ->{
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                viewModel.resetState()
            }
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Verify OTP",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = darkBlue
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "OTP sent to $phone",
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = darkBlue
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = otp,
            onValueChange = { otp = it.filter { ch -> ch.isDigit() } },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Enter OTP") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = darkBlue,
                unfocusedBorderColor = darkBlue.copy(alpha = 0.6f)
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (authState is AuthState.Loading){
            CircularProgressIndicator(color = orange)
        } else {

            Button(
                onClick = {
                    viewModel.verifyOtp(
                        otp = otp.trim(),
                        phoneNumber = phone,
                        role = role
                    )
                },
                enabled = otp.length >= 6,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = darkBlue)
            ) {
                Text(
                    text = "Verify OTP",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

    }


}

