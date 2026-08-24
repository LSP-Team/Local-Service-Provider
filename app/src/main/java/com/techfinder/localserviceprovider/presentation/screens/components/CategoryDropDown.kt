package com.techfinder.localserviceprovider.presentation.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.techfinder.localserviceprovider.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropDown(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    val categories = listOf(
        "Electrician",
        "Plumber",
        "Carpenter",
        "AC Repair",
        "Appliance Repair",
        "Painter",
        "Cleaner",
        "Mechanic",
        "Construction",
        "Beauty & Salon",
        "Tutor",
        "Driver",
        "E-Rickshaw",
        "Rental Vehicle",
        "Gardener",
        "Pest Control",
        "Home Services",
        "Other"
    )

    var expanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = modifier.fillMaxWidth()
    ) {

        OutlinedTextField(
            value = selectedCategory,
            onValueChange = {},
            readOnly = true,

            label = {
                Text("Service Category")
            },

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

//                // Placeholder
//                focusedPlaceholderColor = TextHint,
//                unfocusedPlaceholderColor = TextHint,

                // Icon
                focusedTrailingIconColor = TextSecondary,
                unfocusedTrailingIconColor = TextSecondary,

                // Background
                focusedContainerColor = SurfaceCard,
                unfocusedContainerColor = SurfaceCard,

                // Cursor
                cursorColor = PrimaryBlue
            ),

            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {

            categories.forEach { category ->

                DropdownMenuItem(
                    text = {
                        Text(
                            text = category,
                            color = TextPrimary
                        )
                    },

                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                    },

                    colors = MenuDefaults.itemColors(
                        textColor = TextPrimary
                    )
                )
            }
        }
    }
}