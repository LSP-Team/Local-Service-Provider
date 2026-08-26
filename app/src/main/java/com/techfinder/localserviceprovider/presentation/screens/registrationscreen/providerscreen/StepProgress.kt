package com.techfinder.localserviceprovider.presentation.screens.registrationscreen.providerscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.techfinder.localserviceprovider.ui.theme.BorderSubtle
import com.techfinder.localserviceprovider.ui.theme.PrimaryBlue

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