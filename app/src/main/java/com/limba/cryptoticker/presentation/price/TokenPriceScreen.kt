package com.limba.cryptoticker.presentation.price

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.limba.cryptoticker.R
import com.limba.cryptoticker.presentation.price.TokenPriceViewModel.UiState
import com.limba.cryptoticker.presentation.ui.component.CardRow
import com.limba.cryptoticker.presentation.ui.component.TopBar.SearchToolbar
import com.limba.cryptoticker.presentation.ui.component.TopBar.SearchToolbarState
import com.limba.cryptoticker.presentation.ui.component.TopBar.SimpleToolbar
import com.limba.cryptoticker.presentation.ui.theme.CryptoTickerTheme
import com.limba.cryptoticker.presentation.ui.theme.Dimensions.Padding16

@Composable
fun TickerScreen(viewModel: TokenPriceViewModel) {
    CryptoTickerTheme {
        val uiState = viewModel.uiState.collectAsState().value
        Scaffold(
            topBar = {
                TopBar(
                    uiState = uiState,
                    onSearchQueryChanged = viewModel::onSearchQueryChanged,
                )
            },
        ) { padding ->
            val modifier = Modifier.padding(padding)
            if (uiState.isLoading) {
                LoadingScreen(modifier)
            } else {
                LoadedScreen(
                    modifier = modifier,
                    uiState = uiState,
                )
            }
        }
    }
}

@Composable
fun TopBar(
    uiState: UiState,
    onSearchQueryChanged: (String) -> Unit,
) {
    if (uiState.isLoading) {
        SimpleToolbar(
            title = stringResource(id = R.string.toolbar_title),
        )
    } else {
        SearchToolbar(
            title = stringResource(id = R.string.toolbar_title),
            searchState = uiState.searchState,
            onSearchQueryChanged = onSearchQueryChanged,
        )
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Composable
fun LoadedScreen(
    modifier: Modifier = Modifier,
    uiState: UiState,
) {
    if (uiState.showEmptyScreen) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier.padding(horizontal = Padding16),
                text = stringResource(id = R.string.empty_screen_message),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
            )
        }
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            TokensPriceList(uiState.tokenCurrentPriceData)

            if (uiState.showError) {
                Error()
            }
        }
    }
}

@Composable
fun TokensPriceList(
    tokenCurrentPriceData: List<TokenPriceUiModel>,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(Padding16),
        contentPadding = PaddingValues(
            horizontal = Padding16,
            vertical = Padding16,
        ),
    ) {
        items(tokenCurrentPriceData) {
            CardRow(
                painter = painterResource(it.iconResId),
                header = it.tokenName,
                message = it.tokenSymbol,
                headerTrailingText = it.currentPrice,
            )
        }
    }
}

@Composable
fun BoxScope.Error() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color = Color.Red)
            .align(Alignment.BottomCenter)
    ) {
        Text(
            text = stringResource(id = R.string.update_data_error_message),
            modifier = Modifier
                .padding(horizontal = Padding16)
                .align(Alignment.CenterStart),
            color = Color.White,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadedScreenPreview() {
    CryptoTickerTheme {
        LoadedScreen(
            uiState = UiState(
                tokenCurrentPriceData = getFakeTokenCurrentPriceData(),
                showError = false,
                searchState = SearchToolbarState(),
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadedScreenWithErrorPreview() {
    CryptoTickerTheme {
        LoadedScreen(
            uiState = UiState(
                tokenCurrentPriceData = getFakeTokenCurrentPriceData(),
                showError = true,
                searchState = SearchToolbarState(),
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadedScreenEmptyPreview() {
    CryptoTickerTheme {
        LoadedScreen(
            uiState = UiState(
                tokenCurrentPriceData = emptyList(),
                showError = false,
                searchState = SearchToolbarState(),
            )
        )
    }
}

private fun getFakeTokenCurrentPriceData() = listOf(
    TokenPriceUiModel(
        iconResId = R.drawable.bitpanda_best_logo,
        tokenName = "Bitpanda",
        tokenSymbol = "BEST",
        currentPrice = "$ 2222",
    ),
    TokenPriceUiModel(
        iconResId = R.drawable.eos_eos_logo,
        tokenName = "EOS",
        tokenSymbol = "EOS",
        currentPrice = "$ 3333",
    ),
    TokenPriceUiModel(
        iconResId = R.drawable.swissborg_chsb_logo,
        tokenName = "SwissBorg",
        tokenSymbol = "CHSB",
        currentPrice = "$ 100000",
    ),
)