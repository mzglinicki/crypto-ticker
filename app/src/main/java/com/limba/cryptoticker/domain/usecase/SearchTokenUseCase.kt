package com.limba.cryptoticker.domain.usecase

import com.limba.cryptoticker.domain.entity.TokenCurrentPriceData
import javax.inject.Inject

class SearchTokenUseCase @Inject constructor() {

    operator fun invoke(
        dataToSearch: List<TokenCurrentPriceData>,
        text: String
    ) =
        text.takeIf(String::isNotBlank)?.run {
            dataToSearch.filter {
                it.token.tokenName.contains(text, ignoreCase = true)
            }
        } ?: dataToSearch
}