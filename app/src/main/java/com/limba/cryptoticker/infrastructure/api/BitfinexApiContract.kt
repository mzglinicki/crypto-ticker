package com.limba.cryptoticker.infrastructure.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface BitfinexApiContract {

    @GET("tickers")
    suspend fun getTickers(@Query("symbols") symbols: String): ResponseBody
}