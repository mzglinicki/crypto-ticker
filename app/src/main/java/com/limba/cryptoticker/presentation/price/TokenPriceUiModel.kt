package com.limba.cryptoticker.presentation.price

import androidx.annotation.DrawableRes

data class TokenPriceUiModel(
    @DrawableRes val iconResId: Int,
    val tokenName: String,
    val tokenSymbol: String,
    val currentPrice: String,
)