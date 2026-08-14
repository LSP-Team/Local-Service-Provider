package com.techfinder.localserviceprovider.domain.repository

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
}