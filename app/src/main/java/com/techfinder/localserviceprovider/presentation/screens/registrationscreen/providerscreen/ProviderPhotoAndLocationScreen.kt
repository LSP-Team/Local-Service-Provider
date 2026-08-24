package com.techfinder.localserviceprovider.presentation.screens.registrationscreen.providerscreen

import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.techfinder.localserviceprovider.R
import com.techfinder.localserviceprovider.core.util.createTempImageUri
import com.techfinder.localserviceprovider.core.util.fetchCurrentLocation
import com.techfinder.localserviceprovider.presentation.screens.components.ImageSourceOptionDialog
import com.techfinder.localserviceprovider.presentation.screens.components.LoadingIndicator
import com.techfinder.localserviceprovider.presentation.viewmodel.ProviderRegistrationState
import com.techfinder.localserviceprovider.presentation.viewmodel.ProviderRegistrationViewModel
import com.techfinder.localserviceprovider.ui.theme.StatusSuccessLight
import com.techfinder.localserviceprovider.ui.theme.TextHint

@Composable
fun ProviderPhotoAndLocationScreen(
    onBack: () -> Unit,
    onRegistrationSuccess: () -> Unit,
    viewModel: ProviderRegistrationViewModel = hiltViewModel(),
) {
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }
    var address by remember { mutableStateOf("") }
    var latitude by remember { mutableDoubleStateOf(0.0) }
    var longitude by remember { mutableDoubleStateOf(0.0) }
    var isFetchingLocation by remember { mutableStateOf(false) }

    var showImagePickerDialog by remember { mutableStateOf(false) }


    val context = LocalContext.current
    val registrationState by viewModel.registrationState.collectAsState()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let { profileImageUri = it }
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->

        if (success && tempCameraUri != null){
            profileImageUri = tempCameraUri
        }

    }


    // location permission
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val isGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (isGranted){
            isFetchingLocation = true
            fetchCurrentLocation(
                context = context,
                onLocationFetched = { lat, lng, fetchedAddress ->
                    isFetchingLocation = false
                    latitude = lat
                    longitude = lng

                    if (fetchedAddress.isNotBlank()){
                        address = fetchedAddress
                    }
                },
                onError = { message ->
                    isFetchingLocation = false
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            )
        } else{
            Toast.makeText(context, "Location permission is required to detect area", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(registrationState) {
        when (val state = registrationState) {
            is ProviderRegistrationState.Success -> {
                viewModel.resetState()
                onRegistrationSuccess()
            }
            is ProviderRegistrationState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                viewModel.resetState()
            }
            else -> Unit
        }
    }

    if (showImagePickerDialog) {
        ImageSourceOptionDialog(
            onDismissRequest = { showImagePickerDialog = false },
            onGallerySelect = {
                showImagePickerDialog = false
                imagePickerLauncher.launch("image/*") },
            onCameraSelect = {
                showImagePickerDialog = false
                val uri = context.createTempImageUri()
                tempCameraUri = uri
                cameraLauncher.launch(uri)
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
                .border(2.dp, color = Color.Gray, shape = CircleShape)
                .clickable { showImagePickerDialog = true },
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(profileImageUri ?: R.drawable.defaultprofile)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.defaultprofile),
                error = painterResource(R.drawable.defaultprofile),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        OutlinedButton(
            onClick = {
                locationPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
                .height(150.dp)
        ) {
            if (isFetchingLocation){
                LoadingIndicator(2.dp, Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Detecting location...")
            } else {
                Icon(imageVector = Icons.Default.MyLocation, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Use my current location")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = address,
            onValueChange = { address = it},
            label = { Text("Service Address / Area")},
            modifier = Modifier.fillMaxWidth(),
            singleLine = false,
            maxLines = 3
        )

        if ( latitude != 0.0 && longitude != 0.0){

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Location fetched successfully",
                style = MaterialTheme.typography.bodyMedium,
                color = StatusSuccessLight,
                modifier = Modifier.align(Alignment.Start)
            )

        }


        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (profileImageUri == null){
                    Toast.makeText(context, " Please select a profile photo", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                if (address.isBlank()){
                    Toast.makeText(context, " Please enter your service address", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                if (latitude == 0.0 || longitude == 0.0){
                    Toast.makeText(context, " Please tap 'Use My Current Location' to pin your service area", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                viewModel.saveProfileAndLocation(
                    profileImage = profileImageUri.toString(),
                    address = address.trim(),
                    latitude = latitude,
                    longitude = longitude
                )

                viewModel.registerProvider()
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = registrationState !is ProviderRegistrationState.Loading
        ) {
            Text(
                text = "Complete Registration"
            )
        }


        if (registrationState is ProviderRegistrationState.Loading){
           LoadingIndicator(2.dp, modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}


