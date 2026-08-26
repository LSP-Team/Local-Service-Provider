package com.techfinder.localserviceprovider.presentation.screens.registrationscreen.providerscreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techfinder.localserviceprovider.ui.theme.TextTertiary

@Composable
fun FieldLabel(
    text: String,
    modifier: Modifier = Modifier
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