package com.limba.cryptoticker.domain.usecase

import com.limba.cryptoticker.domain.repository.TokenDataRepository
import javax.inject.Inject

class GetTokensCurrentPriceUseCase @Inject constructor(
    private val tokenDataRepository: TokenDataRepository,
) {

    operator fun invoke() = tokenDataRepository.getTokensCurrentPrice()
}