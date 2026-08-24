package com.techfinder.localserviceprovider.presentation.screens.registrationscreen.loginscreen

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.techfinder.localserviceprovider.presentation.viewmodel.AuthState
import com.techfinder.localserviceprovider.presentation.viewmodel.PhoneAuthViewModel
import com.techfinder.localserviceprovider.ui.theme.AppBackground
import com.techfinder.localserviceprovider.ui.theme.BorderDefault
import com.techfinder.localserviceprovider.ui.theme.BorderFocused
import com.techfinder.localserviceprovider.ui.theme.PrimaryBlue
import com.techfinder.localserviceprovider.ui.theme.TextPrimary
import com.techfinder.localserviceprovider.ui.theme.TextSecondary
import com.techfinder.localserviceprovider.ui.theme.TextHint

@Composable
fun OtpScreen(
    phone: String,
    onAuthSuccess: (String) -> Unit,
    viewModel: PhoneAuthViewModel = hiltViewModel(),
    navHostController: NavHostController
) {

    val context = LocalContext.current

    var otp by remember {
        mutableStateOf("")
    }

    val authState by viewModel.authState.collectAsStateWithLifecycle()

    LaunchedEffect(authState) {

        when (val state = authState) {

            is AuthState.Success -> {

                onAuthSuccess(state.user.role)

                viewModel.resetState()
            }

            is AuthState.Error -> {

                Toast.makeText(
                    context,
                    state.message,
                    Toast.LENGTH_SHORT
                ).show()

                viewModel.resetState()
            }

            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Verify OTP",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "OTP sent to $phone",
            fontSize = 14.sp,
            color = TextSecondary
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        OutlinedTextField(
            value = otp,

            onValueChange = { newValue ->

                val digitsOnly = newValue.filter {
                    it.isDigit()
                }

                if (digitsOnly.length <= 6) {
                    otp = digitsOnly
                }
            },

            modifier = Modifier.fillMaxWidth(),

            label = {
                Text("Enter OTP")
            },

            placeholder = {
                Text("6 digit OTP")
            },

            singleLine = true,

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),

            shape = RoundedCornerShape(10.dp),

            colors = OutlinedTextFieldDefaults.colors(

                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,

                focusedLabelColor = TextSecondary,
                unfocusedLabelColor = TextSecondary,

                focusedPlaceholderColor = TextHint,
                unfocusedPlaceholderColor = TextHint,

                focusedBorderColor = BorderFocused,
                unfocusedBorderColor = BorderDefault,

                cursorColor = PrimaryBlue
            )
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        if (authState is AuthState.Loading) {

            CircularProgressIndicator(
                color = PrimaryBlue
            )

        } else {

            Button(
                onClick = {

                    viewModel.verifyOtp(
                        otp = otp.trim(),
                        phoneNumber = phone
                    )
                },

                enabled = otp.length == 6,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),

                shape = RoundedCornerShape(10.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlue,
                    contentColor = TextPrimary
                )
            ) {

                Text(
                    text = "Verify OTP",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}