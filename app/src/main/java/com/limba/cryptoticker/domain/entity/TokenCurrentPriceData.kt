package com.limba.cryptoticker.domain.entity

import com.limba.cryptoticker.domain.utils.SupportedToken

data class TokenCurrentPriceData(
    val token: SupportedToken,
    val currentPrice: Float,
)