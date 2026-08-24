package com.techfinder.localserviceprovider.presentation.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.techfinder.localserviceprovider.ui.theme.BorderDefault
import com.techfinder.localserviceprovider.ui.theme.BorderFocused
import com.techfinder.localserviceprovider.ui.theme.PrimaryBlue
import com.techfinder.localserviceprovider.ui.theme.SurfaceCard
import com.techfinder.localserviceprovider.ui.theme.TextHint
import com.techfinder.localserviceprovider.ui.theme.TextPrimary
import com.techfinder.localserviceprovider.ui.theme.TextSecondary


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExperienceDropDown(
    selectedExperience: Int?,
    onSelectedExperience: (Int) -> Unit,
    modifier: Modifier = Modifier
){

    val experiences = listOf(
        0 to "Less than 1 year",
        1 to "1 year",
        2 to "2 years",
        3 to "3 years",
        4 to "4 years",
        5 to "5 years",
        6 to "6 years",
        7 to "7 years",
        8 to "8 years",
        9 to "9 years",
        10 to "10+ years"
    )
    val selectedTex = experiences
        .find { it.first == selectedExperience }
        ?.second
        ?: "Select experience"

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = Modifier
    ) {

        OutlinedTextField(
            value = selectedTex,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                // Text
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,

                // Label
                focusedLabelColor = TextSecondary,
                unfocusedLabelColor = TextSecondary,

                // Border
                focusedBorderColor = BorderFocused,
                unfocusedBorderColor = BorderDefault,

                // Icon
                focusedTrailingIconColor = TextSecondary,
                unfocusedTrailingIconColor = TextSecondary,

                // Background
                focusedContainerColor = SurfaceCard,
                unfocusedContainerColor = SurfaceCard,

                // Cursor
                cursorColor = PrimaryBlue
            )

        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {

            experiences.forEach { (years, text) ->

                DropdownMenuItem(
                    text ={
                        Text(text)
                    },
                    onClick = {
                        onSelectedExperience(years)
                        expanded = false
                    }
                )
            }
        }
    }
}