package com.techfinder.localserviceprovider.data.repository

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.techfinder.localserviceprovider.domain.model.ProviderModel
import com.techfinder.localserviceprovider.domain.repository.ProviderRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProviderRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
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

    override suspend fun uploadProfileImage(
        uid: String,
        imageUri: Uri
    ): Result<String> {

        return try {
            val imageRef = storage
                .reference
                .child("providers/$uid/profile.jpg")

            imageRef
                .putFile(imageUri)
                .await()

            val downloadUrl =
                imageRef.downloadUrl
                    .await().toString()

            Result.success(downloadUrl)
        } catch (e: Exception){
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
                    if (snapshot != null && snapshot.exists()) {
                        snapshot.toObject(ProviderModel::class.java)

                    } else{
                        null
                    }
                        trySend(provider)
            }

        awaitClose{
            listenerRegistration.remove()
        }
    }

}