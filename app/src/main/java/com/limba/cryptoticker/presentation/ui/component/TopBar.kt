package com.limba.cryptoticker.presentation.ui.component

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.limba.cryptoticker.R
import com.limba.cryptoticker.presentation.ui.component.TopBar.SearchToolbar
import com.limba.cryptoticker.presentation.ui.component.TopBar.SearchToolbarState
import com.limba.cryptoticker.presentation.ui.component.TopBar.SimpleToolbar
import com.limba.cryptoticker.presentation.ui.theme.CryptoTickerTheme
import com.limba.cryptoticker.presentation.ui.theme.Dimensions.Padding16

object TopBar {

    @Composable
    fun SearchToolbar(
        title: String,
        searchState: SearchToolbarState,
        onSearchQueryChanged: (String) -> Unit,
    ) {
        TopAppBar(
            title = {
                SlideInRight(visible = searchState.isSearchOpen) {
                    ToolbarSearch(
                        searchState = searchState,
                        onSearchQueryChanged = onSearchQueryChanged,
                    )
                }
                SlideOutRight(visible = !searchState.isSearchOpen) {
                    Text(text = title)
                }
            },
            actions = {
                ToolbarActions(searchState)
            },
        )
    }

    @Composable
    private fun ToolbarActions(
        searchState: SearchToolbarState,
    ) {
        if (!searchState.isSearchOpen) {
            IconButton(onClick = searchState::onSearchOpened) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    tint = Color.White,
                    contentDescription = stringResource(
                        id = R.string.search_icon_content_description
                    ),
                )
            }
        }
    }

    @Composable
    private fun ToolbarSearch(
        searchState: SearchToolbarState,
        onSearchQueryChanged: (String) -> Unit,
    ) {
        val focusManager = LocalFocusManager.current
        val searchVerticalPadding = 2.dp
        TextField(
            value = searchState.searchQuery,
            onValueChange = { textFieldValue ->
                if (textFieldValue != searchState.searchQuery) {
                    onSearchQueryChanged(textFieldValue)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = searchVerticalPadding,
                    bottom = searchVerticalPadding,
                    end = Padding16,
                )
                .background(
                    color = MaterialTheme.colors.background,
                    shape = CircleShape,
                )
                .clip(CircleShape),
            textStyle = MaterialTheme.typography.body1,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_hint),
                    style = MaterialTheme.typography.body2,
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            maxLines = 1,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                textColor = Color.Black,
            ),
            trailingIcon = {
                IconButton(
                    onClick = {
                        focusManager.clearFocus()
                        onSearchQueryChanged("")
                        searchState.onSearchClosed()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        tint = Color.Black,
                        contentDescription = stringResource(
                            id = R.string.close_search_icon_content_description
                        ),
                    )
                }
            }
        )
    }

    class SearchToolbarState(
        searchQuery: String = "",
        isSearchOpen: Boolean = false,
    ) {
        var searchQuery by mutableStateOf(searchQuery)
        var isSearchOpen by mutableStateOf(isSearchOpen)

        fun onSearchOpened() {
            isSearchOpen = true
        }

        fun onSearchClosed() {
            isSearchOpen = false
        }

        fun onSearchQueryChanged(query: String) {
            searchQuery = query
        }
    }

    @Composable
    fun SimpleToolbar(
        title: String,
    ) {
        TopAppBar(
            title = {
                Text(text = title)
            },
        )
    }
}

@Composable
private fun SlideInRight(
    visible: Boolean,
    content: @Composable () -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInHorizontally { it },
        exit = fadeOut() + slideOutHorizontally { it },
        content = { content() },
    )
}

@Composable
private fun SlideOutRight(
    visible: Boolean,
    content: @Composable () -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + expandHorizontally(),
        exit = fadeOut() + shrinkHorizontally(),
        content = { content() },
    )
}

@Preview(showBackground = true)
@Composable
fun SimpleToolbarPreview() {
    CryptoTickerTheme {
        SimpleToolbar(
            title = "Token price"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ClosedSearchToolbarPreview() {
    CryptoTickerTheme {
        SearchToolbar(
            title = "Token price",
            searchState = SearchToolbarState(),
            onSearchQueryChanged = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OpenedSearchToolbarPreview() {
    CryptoTickerTheme {
        SearchToolbar(
            title = "Token price",
            searchState = SearchToolbarState(
                isSearchOpen = true,
            ),
            onSearchQueryChanged = {},
        )
    }
}
