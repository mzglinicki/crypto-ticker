package com.limba.cryptoticker.presentation.price

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TokenPriceActivity : ComponentActivity() {

    private val viewModel: TokenPriceViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TickerScreen(viewModel)
        }
    }
}