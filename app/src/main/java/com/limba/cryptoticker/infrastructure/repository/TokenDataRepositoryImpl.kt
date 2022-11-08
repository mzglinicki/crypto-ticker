package com.limba.cryptoticker.infrastructure.repository

import com.limba.cryptoticker.domain.entity.TokenCurrentPriceData
import com.limba.cryptoticker.domain.repository.TokenDataRepository
import com.limba.cryptoticker.domain.utils.GetTokensCurrentPriceResult.Error
import com.limba.cryptoticker.domain.utils.GetTokensCurrentPriceResult.Success
import com.limba.cryptoticker.domain.utils.SupportedToken
import com.limba.cryptoticker.infrastructure.api.BitfinexApiClient
import com.limba.cryptoticker.infrastructure.api.TickerDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenDataRepositoryImpl @Inject constructor(
    private val bitfinexApiClient: BitfinexApiClient,
) : TokenDataRepository {

    override fun getTokensCurrentPrice() = flow {
        while (true) {
            val tokensCurrentPrice = fetchTokensCurrentPrice()
            emit(tokensCurrentPrice)
            delay(REFRESH_INTERVAL_MS)
        }
    }

    private suspend fun fetchTokensCurrentPrice() =
        runCatching {
            val tokensTokenCurrentPriceData = bitfinexApiClient
                .getTickers(getSupportedTokensSymbols())
                .mapToDomainEntity()
            Success(tokensTokenCurrentPriceData)
        }.getOrDefault(Error)

    private fun List<TickerDto>.mapToDomainEntity() = map {
        TokenCurrentPriceData(
            token = SupportedToken.getByTickerUsdSymbol(it.symbol),
            currentPrice = it.lastPrice,
        )
    }

    private fun getSupportedTokensSymbols() =
        SupportedToken.values().joinToString(",") { it.tickerUsdSymbol }

    companion object {
        const val REFRESH_INTERVAL_MS = 5000L
    }
}