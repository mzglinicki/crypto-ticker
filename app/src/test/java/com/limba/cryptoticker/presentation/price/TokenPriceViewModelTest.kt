package com.limba.cryptoticker.presentation.price

import com.limba.cryptoticker.domain.entity.TokenCurrentPriceData
import com.limba.cryptoticker.domain.usecase.GetTokensCurrentPriceUseCase
import com.limba.cryptoticker.domain.usecase.SearchTokenUseCase
import com.limba.cryptoticker.domain.utils.GetTokensCurrentPriceResult.Error
import com.limba.cryptoticker.domain.utils.GetTokensCurrentPriceResult.Success
import com.limba.cryptoticker.domain.utils.SupportedToken.BITCOIN
import com.limba.cryptoticker.domain.utils.SupportedToken.SWISS_BORG
import com.limba.cryptoticker.domain.utils.TestDataFactory.createTokenCurrentPriceData
import com.limba.cryptoticker.presentation.price.TokenPriceViewModel.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
internal class TokenPriceViewModelTest {

    private val getTokensCurrentPrice = mock(GetTokensCurrentPriceUseCase::class.java)
    private val searchToken = mock(SearchTokenUseCase::class.java)

    @BeforeEach
    fun beforeEach() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterEach
    fun afterEach() {
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN there is no tokens current price data THEN empty screen is displayed`() = runTest {
        `when`(getTokensCurrentPrice()).thenReturn(flow { emit(Success(emptyList())) })

        val viewModel = createViewModel()
        runCurrent()

        assertTrue(viewModel.uiState.value.showEmptyScreen)
    }

    @Test
    fun `WHEN loading tokens current price data returns error THEN error is displayed`() = runTest {
        `when`(getTokensCurrentPrice()).thenReturn(flow { emit(Error) })

        val viewModel = createViewModel()
        val expectedState = viewModel.uiState.value.copy(
            isLoading = false,
            showError = true,
        )
        runCurrent()

        viewModel.uiState.value shouldBe expectedState
    }

    @Test
    fun `WHEN loading tokens current price data returns success THEN returned tokens are displayed`() =
        runTest {
            val tokensTokenCurrentPriceData = listOf(
                createTokenCurrentPriceData(BITCOIN),
                createTokenCurrentPriceData(SWISS_BORG),
            )
            `when`(getTokensCurrentPrice()).thenReturn(
                flow { emit(Success(tokensTokenCurrentPriceData)) }
            )

            val viewModel = createViewModel()
            val expectedState = viewModel.uiState.value.copy(
                isLoading = false,
                tokenCurrentPriceData = tokensTokenCurrentPriceData.mapToStateModels()
            )
            runCurrent()

            viewModel.uiState.value shouldBe expectedState
        }

    private fun createViewModel() = TokenPriceViewModel(
        getTokensCurrentPrice = getTokensCurrentPrice,
        searchToken = searchToken,
    )

    private fun List<TokenCurrentPriceData>.mapToStateModels() = map {
        TokenPriceUiModel(
            iconResId = it.token.logoResId,
            tokenName = it.token.tokenName,
            tokenSymbol = it.token.tokenSymbol,
            currentPrice = "$ ${it.currentPrice}",
        )
    }

    private infix fun UiState.shouldBe(expectedState: UiState) {
        assertEquals(expectedState, this)
    }
}
