package com.techfinder.localserviceprovider.domain.usecases.providerusecase

import android.net.Uri
import com.techfinder.localserviceprovider.domain.repository.ProviderRepository
import javax.inject.Inject

class UploadProviderProfileImageUseCase @Inject constructor(
    private val repository: ProviderRepository
) {

    suspend operator fun invoke(
        uid: String,
        imageUri: Uri
    ): Result<String> {

        return repository.uploadProfileImage(
            uid = uid,
            imageUri = imageUri
        )
    }
}