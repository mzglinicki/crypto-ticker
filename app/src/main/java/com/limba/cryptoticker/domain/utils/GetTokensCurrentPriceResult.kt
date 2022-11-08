package com.limba.cryptoticker.domain.utils

import com.limba.cryptoticker.domain.entity.TokenCurrentPriceData

sealed interface GetTokensCurrentPriceResult {

    data class Success(
        val tokensTokenCurrentPriceData: List<TokenCurrentPriceData>,
    ) : GetTokensCurrentPriceResult

    object Error : GetTokensCurrentPriceResult
}