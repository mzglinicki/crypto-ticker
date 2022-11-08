package com.limba.cryptoticker.presentation.price

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.limba.cryptoticker.domain.entity.TokenCurrentPriceData
import com.limba.cryptoticker.domain.usecase.GetTokensCurrentPriceUseCase
import com.limba.cryptoticker.domain.usecase.SearchTokenUseCase
import com.limba.cryptoticker.domain.utils.GetTokensCurrentPriceResult.Error
import com.limba.cryptoticker.domain.utils.GetTokensCurrentPriceResult.Success
import com.limba.cryptoticker.presentation.ui.component.TopBar.SearchToolbarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TokenPriceViewModel @Inject constructor(
    private val searchToken: SearchTokenUseCase,
    getTokensCurrentPrice: GetTokensCurrentPriceUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState(isLoading = true))
    val uiState: StateFlow<UiState> = _uiState

    private var tokensTokenCurrentPriceData = listOf<TokenCurrentPriceData>()

    init {
        viewModelScope.launch {
            getTokensCurrentPrice().collect { result ->
                if (result is Success) {
                    tokensTokenCurrentPriceData = result.tokensTokenCurrentPriceData
                }
                updateState(
                    tokenCurrentPriceData = tokensTokenCurrentPriceData.map(::mapToUiModel),
                    showError = result is Error,
                )
            }
        }
    }

    private fun updateState(
        tokenCurrentPriceData: List<TokenPriceUiModel>,
        showError: Boolean,
    ) {
        val currentState = _uiState.value
        if (currentState.searchState.isSearchOpen) return

        _uiState.value = currentState.copy(
            tokenCurrentPriceData = tokenCurrentPriceData,
            showError = showError,
            isLoading = false,
        )
    }

    fun onSearchQueryChanged(text: String) {
        val currentState = _uiState.value

        currentState.searchState.onSearchQueryChanged(text)
        _uiState.value = currentState.copy(
            tokenCurrentPriceData = searchToken(tokensTokenCurrentPriceData, text)
                .map(::mapToUiModel),
        )
    }

    private fun mapToUiModel(tokenCurrentPriceData: TokenCurrentPriceData) =
        tokenCurrentPriceData.run {
            TokenPriceUiModel(
                iconResId = token.logoResId,
                tokenName = token.tokenName,
                tokenSymbol = token.tokenSymbol,
                currentPrice = "$USD_SYMBOL $currentPrice",
            )
        }

    data class UiState(
        val isLoading: Boolean = false,
        val searchState: SearchToolbarState = SearchToolbarState(),
        val tokenCurrentPriceData: List<TokenPriceUiModel> = emptyList(),
        val showError: Boolean = false,
    ) {
        val showEmptyScreen = tokenCurrentPriceData.isEmpty()
        val showSearch = !isLoading && !showEmptyScreen
    }

    private companion object {
        const val USD_SYMBOL = "$"
    }
}