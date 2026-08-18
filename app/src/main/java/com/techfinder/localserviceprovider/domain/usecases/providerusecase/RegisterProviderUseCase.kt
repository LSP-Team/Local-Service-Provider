package com.techfinder.localserviceprovider.domain.usecases.providerusecase

import com.techfinder.localserviceprovider.domain.model.ProviderModel
import com.techfinder.localserviceprovider.domain.repository.ProviderRepository
import javax.inject.Inject

class RegisterProviderUseCase @Inject constructor(
    private val repository: ProviderRepository
) {

    suspend operator fun invoke(
        provider: ProviderModel
    ): Result<Unit> {

        return repository.registerProvider(provider)
    }
}