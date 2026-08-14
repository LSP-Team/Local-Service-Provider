package com.techfinder.localserviceprovider.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.techfinder.localserviceprovider.domain.model.ProviderModel
import com.techfinder.localserviceprovider.domain.repository.ProviderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProviderRepositoryImp @Inject constructor(
    private val firestore: FirebaseFirestore
): ProviderRepository {
    override suspend fun registerProvider(provider: ProviderModel): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getProvider(uid: String): Result<ProviderModel?> {
        TODO("Not yet implemented")
    }

    override fun getProviderFlow(uid: String): Flow<ProviderModel?> {
        TODO("Not yet implemented")
    }


}