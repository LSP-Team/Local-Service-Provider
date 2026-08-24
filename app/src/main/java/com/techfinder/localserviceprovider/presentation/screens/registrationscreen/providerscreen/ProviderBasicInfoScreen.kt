package com.techfinder.localserviceprovider.presentation.screens.registrationscreen.providerscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.techfinder.localserviceprovider.presentation.screens.components.CategoryDropDown
import com.techfinder.localserviceprovider.presentation.screens.components.ExperienceDropDown
import com.techfinder.localserviceprovider.presentation.viewmodel.ProviderRegistrationViewModel
import com.techfinder.localserviceprovider.ui.theme.*

@Composable
fun ProviderBasicInfoScreen(
    onNext: () -> Unit,
    onBack: () -> Unit,
    viewModel: ProviderRegistrationViewModel = hiltViewModel(),
) {
    var name        by remember { mutableStateOf("") }
    var category    by remember { mutableStateOf("") }
    var experience  by remember { mutableStateOf<Int?>(null) }
    var description by remember { mutableStateOf("") }

    val phone = remember {
        viewModel.getAuthenticatedPhone()
    }

    val isFormValid = name.isNotBlank()
            && name.length <= 50
            && category.isNotBlank()
            && experience != null
            && description.trim().length >= 20

    val descCharCount = description.length
    val descWarning   = descCharCount > 450

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground),
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            PrimaryBlueGlow,
                            Color.Transparent,
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .windowInsetsPadding(WindowInsets.statusBars)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(horizontal = 20.dp),
        ) {

            Spacer(Modifier.height(8.dp))

            // Top bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(SurfaceRaised),
                ) {
                    Icon(
                        imageVector        = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint               = TextPrimary,
                        modifier           = Modifier.size(20.dp),
                    )
                }
                Spacer(Modifier.weight(1f))
                // Step
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(PrimaryBlueLight)
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                ) {
                    Text(
                        text     = "STEP 1 OF 2",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color    = PrimaryBlue,
                        letterSpacing = 1.sp,
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            StepProgressBar(currentStep = 1, totalSteps = 2)

            Spacer(Modifier.height(22.dp))

            Text(
                text       = "Register as Provider",
                fontSize   = 28.sp,
                fontWeight = FontWeight.Bold,
                color      = TextPrimary,
                lineHeight = 34.sp,
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text     = "Tell us about yourself and your service",
                fontSize = 13.sp,
                color    = TextSecondary,
            )

            Spacer(Modifier.height(28.dp))

            FieldLabel(text = "FULL NAME")
            ProviderTextField(
                value         = name,
                onValueChange = { if (it.length <= 50) name = it },
                placeholder   = "Enter your full name",
                singleLine    = true,
            )

            Spacer(Modifier.height(18.dp))

            FieldLabel(text = "PHONE NUMBER")
            ProviderTextField(
                value         = phone ,
                onValueChange = {},
                placeholder   = "",
                singleLine    = true,
                readOnly      = true,
                trailingIcon  = {
                    Icon(
                        imageVector        = Icons.Default.CheckCircle,
                        contentDescription = "Verified",
                        tint               = AccentTeal,
                        modifier           = Modifier.size(18.dp),
                    )
                }
            )

            Spacer(Modifier.height(18.dp))

            FieldLabel(text = "SERVICE CATEGORY")
            CategoryDropDown(
                selectedCategory   = category,
                onCategorySelected = { category = it },
            )

            Spacer(Modifier.height(18.dp))

            FieldLabel(text = "YEARS OF EXPERIENCE")

            ExperienceDropDown(
                selectedExperience   = experience,
                onSelectedExperience = { experience = it },
            )

            Spacer(Modifier.height(18.dp))


            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically,
            ) {
                FieldLabel(text = "ABOUT YOUR SERVICE")
                // Character count
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            if (descWarning) AccentAmberLight else SurfaceRaised
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp),
                ) {
                    Text(
                        text      = "$descCharCount / 500",
                        fontSize  = 10.sp,
                        color     = if (descWarning) AccentAmber else TextTertiary,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }

            OutlinedTextField(
                value         = description,
                onValueChange = { if (it.length <= 500) description = it },
                modifier      = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                placeholder   = {
                    Text(
                        text  = "Describe your services, experience and expertise…",
                        color = TextHint,
                        fontSize = 13.sp,
                    )
                },
                minLines = 3,
                maxLines = 7,
                shape    = RoundedCornerShape(12.dp),
                colors   = providerTextFieldColors(),
            )

            // Minimum length hint
            AnimatedVisibility(
                visible = description.isNotEmpty() && description.trim().length < 20,
                enter   = fadeIn() + slideInVertically(),
            ) {
                Text(
                    text     = "Minimum 20 characters required",
                    fontSize = 11.sp,
                    color    = StatusError,
                    modifier = Modifier.padding(top = 4.dp, start = 4.dp),
                )
            }

            Spacer(Modifier.height(28.dp))

           // continue button
            Button(
                onClick = {
                    experience?.let {

                        viewModel.saveBasicInfo(
                            name = name.trim(),
                            category = category,
                            experienceYears = it,
                            description = description.trim()
                        )

                        onNext()
                    }
                },
                enabled  = isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape  = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor         = AccentTeal,
                    contentColor           = TextOnAccent,
                    disabledContainerColor = DisabledContainer,
                    disabledContentColor   = DisabledContent,
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                ),
            ) {
                Text(
                    text       = "Continue",
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.3.sp,
                )
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}


@Composable
fun StepProgressBar(
    currentStep: Int,
    totalSteps:  Int,
    modifier:    Modifier = Modifier,
) {
    Row(
        modifier              = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        repeat(totalSteps) { idx ->
            val filled = idx < currentStep
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(3.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        if (filled) PrimaryBlue else BorderSubtle
                    )
            )
        }
    }
}


@Composable
fun FieldLabel(
    text:     String,
    modifier: Modifier = Modifier,
) {
    Text(
        text          = text,
        fontSize      = 10.sp,
        fontWeight    = FontWeight.Medium,
        color         = TextTertiary,
        letterSpacing = 1.5.sp,
        modifier      = modifier.padding(bottom = 7.dp),
    )
}


@Composable
fun ProviderTextField(
    value:         String,
    onValueChange: (String) -> Unit,
    placeholder:   String,
    modifier:      Modifier = Modifier,
    singleLine:    Boolean  = false,
    readOnly:      Boolean  = false,
    trailingIcon:  (@Composable () -> Unit)? = null,
) {
    OutlinedTextField(
        value         = value,
        onValueChange = onValueChange,
        readOnly      = readOnly,
        modifier      = modifier.fillMaxWidth(),
        placeholder   = {
            Text(
                text     = placeholder,
                color    = TextHint,
                fontSize = 13.sp,
            )
        },
        trailingIcon  = trailingIcon,
        singleLine    = singleLine,
        shape         = RoundedCornerShape(12.dp),
        colors        = providerTextFieldColors(),
    )
}


@Composable
fun providerTextFieldColors() = OutlinedTextFieldDefaults.colors(
    // Text
    focusedTextColor   = TextPrimary,
    unfocusedTextColor = TextPrimary,
    disabledTextColor  = TextTertiary,

    // Container
    focusedContainerColor   = SurfaceRaised,
    unfocusedContainerColor = SurfaceCard,
    disabledContainerColor  = DisabledContainer,

    // Border
    focusedBorderColor   = PrimaryBlue,
    unfocusedBorderColor = BorderDefault,
    disabledBorderColor  = BorderSubtle,

    // Placeholder
    focusedPlaceholderColor   = TextHint,
    unfocusedPlaceholderColor = TextHint,

    // Cursor
    cursorColor = PrimaryBlue,

    // Error
    errorBorderColor = StatusError,
    errorTextColor   = TextPrimary,
)


//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun ProviderBasicInfoScreenPreview() {
//    LocalServiceProviderTheme {
//        ProviderBasicInfoScreen(
//            phone  = "+91 98765 43210",
//            onNext = {},
//            onBack = {},
//        )
//    }
//}