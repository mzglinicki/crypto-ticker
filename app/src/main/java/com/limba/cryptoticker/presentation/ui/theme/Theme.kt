package com.limba.cryptoticker.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DarkColorPalette = darkColors(
    primary = Green700,
    primaryVariant = Green900,
    secondary = Green200,
    background = Gray200,
)

private val LightColorPalette = lightColors(
    primary = Green700,
    primaryVariant = Green900,
    secondary = Green200,
    background = Gray200,
)

@Composable
fun CryptoTickerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography(
            body1 = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            ),
            body2 = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                fontSize = 14.sp
            ),
        ),
        shapes = Shapes(
            small = RoundedCornerShape(2.dp),
            medium = RoundedCornerShape(12.dp),
            large = RoundedCornerShape(0.dp)
        ),
        content = content
    )
}