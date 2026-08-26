package com.techfinder.localserviceprovider.presentation.screens.registrationscreen.providerscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techfinder.localserviceprovider.ui.theme.PrimaryBlue
import com.techfinder.localserviceprovider.ui.theme.PrimaryBlueLight
import com.techfinder.localserviceprovider.ui.theme.SurfaceRaised
import com.techfinder.localserviceprovider.ui.theme.TextPrimary

@Composable
fun TopAppBarDefaults(
    onBack: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
){

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
                text     = text,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color    = PrimaryBlue,
                letterSpacing = 1.sp,
            )
        }
    }

}