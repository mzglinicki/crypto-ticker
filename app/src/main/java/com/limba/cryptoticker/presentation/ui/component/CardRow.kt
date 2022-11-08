package com.limba.cryptoticker.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.limba.cryptoticker.R
import com.limba.cryptoticker.presentation.ui.theme.CryptoTickerTheme
import com.limba.cryptoticker.presentation.ui.theme.Dimensions.Padding16

@Composable
fun CardRow(
    painter: Painter,
    header: String,
    headerTrailingText: String,
    message: String,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 1.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Padding16),
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
            )
            Spacer(modifier = Modifier.size(Padding16))

            Column(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(),
                verticalArrangement = SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = SpaceBetween,
                ) {
                    Text(
                        text = header,
                        style = MaterialTheme.typography.body1,
                    )
                    Text(
                        text = headerTrailingText,
                        style = MaterialTheme.typography.body1,
                    )
                }
                Text(
                    text = message,
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardRowPreview() {
    CryptoTickerTheme {
        CardRow(
            header = "Header",
            message = "message",
            headerTrailingText = "headerTrailingText",
            painter = painterResource(R.drawable.swissborg_chsb_logo),
        )
    }
}