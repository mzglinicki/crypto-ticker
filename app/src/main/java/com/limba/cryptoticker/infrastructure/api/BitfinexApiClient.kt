package com.limba.cryptoticker.infrastructure.api

import com.google.gson.JsonArray
import com.google.gson.JsonParser
import java.io.InputStreamReader
import javax.inject.Inject

class BitfinexApiClient @Inject constructor(
    private val bitfinexApiContract: BitfinexApiContract,
) {

    suspend fun getTickers(currencyPairs: String): List<TickerDto> {
        val responseBody = bitfinexApiContract.getTickers(currencyPairs)
        val tickers = mutableListOf<TickerDto>()
        JsonParser().parse(InputStreamReader(responseBody.byteStream()))
            .asJsonArray.iterator()
            .forEach { tickerData ->
                tickers.add(parseToTicker(tickerData.asJsonArray))
            }
        return tickers
    }

    private fun parseToTicker(data: JsonArray) = data.run {
        TickerDto(
            symbol = get(0).asString,
            bid = get(1).asFloat,
            bidSize = get(2).asFloat,
            ask = get(3).asFloat,
            askSize = get(4).asFloat,
            dailyChange = get(5).asFloat,
            dailyChangeRelative = get(6).asFloat,
            lastPrice = get(7).asFloat,
            volume = get(8).asFloat,
            high = get(9).asFloat,
            low = get(10).asFloat,
        )
    }
}