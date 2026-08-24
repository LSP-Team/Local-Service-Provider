package com.techfinder.localserviceprovider.domain.repository

import android.net.Uri
import com.techfinder.localserviceprovider.domain.model.ProviderModel
import kotlinx.coroutines.flow.Flow

interface ProviderRepository {

    suspend fun registerProvider(
        provider: ProviderModel
    ): Result<Unit>

    suspend fun getProvider(
        uid: String
    ): Result<ProviderModel?>

    // Real-time observation
    fun getProviderFlow(
        uid: String
    ): Flow<ProviderModel?>

    suspend fun uploadProfileImage(
        uid: String,
        imageUri: Uri
    ): Result<String>

}