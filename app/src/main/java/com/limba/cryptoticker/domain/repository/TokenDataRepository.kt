package com.limba.cryptoticker.domain.repository

import com.limba.cryptoticker.domain.utils.GetTokensCurrentPriceResult
import kotlinx.coroutines.flow.Flow

interface TokenDataRepository {

    fun getTokensCurrentPrice(): Flow<GetTokensCurrentPriceResult>
}