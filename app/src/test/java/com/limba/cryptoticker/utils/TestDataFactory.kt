package com.limba.cryptoticker.utils

import com.limba.cryptoticker.domain.entity.TokenCurrentPriceData
import com.limba.cryptoticker.domain.utils.SupportedToken
import com.limba.cryptoticker.infrastructure.api.TickerDto

object TestDataFactory {

    fun createTokenCurrentPriceData(token: SupportedToken) = TokenCurrentPriceData(
        token = token,
        currentPrice = 1f,
    )

    fun createTickerDto(supportedToken: SupportedToken) = TickerDto(
        symbol = supportedToken.tickerUsdSymbol,
        bid = 1f,
        bidSize = 1f,
        ask = 1f,
        askSize = 1f,
        dailyChange = 1f,
        dailyChangeRelative = 1f,
        lastPrice = 1f,
        volume = 1f,
        high = 1f,
        low = 1f,
    )
}