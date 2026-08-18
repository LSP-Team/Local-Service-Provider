package com.techfinder.localserviceprovider.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.techfinder.localserviceprovider.domain.model.ProviderModel
import com.techfinder.localserviceprovider.domain.repository.ProviderRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProviderRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): ProviderRepository {

    private val providerCollection = firestore.collection("providers")
    override suspend fun registerProvider(provider: ProviderModel): Result<Unit> {

        return try {
            providerCollection
                .document(provider.uid)
                .set(provider)
                .await()

            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getProvider(uid: String): Result<ProviderModel?> {
        return try {

            val snapshot = providerCollection
                .document(uid)
                .get()
                .await()

            val provider = snapshot
                .toObject(ProviderModel::class.java)

            Result.success(provider)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override fun getProviderFlow(uid: String): Flow<ProviderModel?> = callbackFlow{

        val listenerRegistration = providerCollection
            .document(uid)
            .addSnapshotListener { snapshot, error ->

                if (error != null){
                    close(error)
                    return@addSnapshotListener
                }

                val provider =
                    snapshot?.toObject(ProviderModel::class.java)

                trySend(provider)
            }

        awaitClose{
            listenerRegistration.remove()
        }
    }


}