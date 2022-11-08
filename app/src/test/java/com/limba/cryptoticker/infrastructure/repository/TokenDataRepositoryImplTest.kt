package com.limba.cryptoticker.infrastructure.repository

import app.cash.turbine.test
import com.limba.cryptoticker.domain.utils.GetTokensCurrentPriceResult.*
import com.limba.cryptoticker.utils.TestDataFactory.createTickerDto
import com.limba.cryptoticker.utils.TestDataFactory.createTokenCurrentPriceData
import com.limba.cryptoticker.domain.utils.SupportedToken.FILECOIN
import com.limba.cryptoticker.infrastructure.api.BitfinexApiClient
import com.limba.cryptoticker.infrastructure.repository.TokenDataRepositoryImpl.Companion.REFRESH_INTERVAL_MS
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
internal class TokenDataRepositoryImplTest {

    private val bitfinexApiClient = mock(BitfinexApiClient::class.java)
    private val repository = TokenDataRepositoryImpl(
        bitfinexApiClient = bitfinexApiClient,
    )

    @Test
    fun `WHEN api call throws an exception THEN error is emitted`() = runTest {
        `when`(bitfinexApiClient.getTickers(anyString())).thenAnswer {
            throw Exception()
        }

        repository.getTokensCurrentPrice().test {
            assertTrue(awaitItem() is Error)
        }
    }

    @Test
    fun `WHEN api call returns an empty list THEN success result with empty list is emitted`() =
        runTest {
            `when`(bitfinexApiClient.getTickers(anyString())).thenReturn(emptyList())

            repository.getTokensCurrentPrice().test {
                assertEquals(Success(emptyList()), awaitItem())
            }
        }

    @Test
    fun `WHEN refresh interval not passed THEN new data should not be fetched`() = runTest {
        `when`(bitfinexApiClient.getTickers(anyString())).thenReturn(emptyList())

        repository.getTokensCurrentPrice().test {
            assertEquals(Success(emptyList()), awaitItem())

            delay(REFRESH_INTERVAL_MS - 1)
            expectNoEvents()
        }
    }

    @Test
    fun `WHEN refresh interval passed THEN new data should be fetched`() = runTest {
        `when`(bitfinexApiClient.getTickers(anyString())).thenReturn(emptyList())

        val secondIntervalTickers = listOf(createTickerDto(FILECOIN))
        val expectedSecondIntervalResult = listOf(createTokenCurrentPriceData(FILECOIN))

        repository.getTokensCurrentPrice().test {
            assertEquals(Success(emptyList()), awaitItem())
            expectNoEvents()

            `when`(bitfinexApiClient.getTickers(anyString())).thenReturn(secondIntervalTickers)
            delay(REFRESH_INTERVAL_MS)
            assertEquals(Success(expectedSecondIntervalResult), expectMostRecentItem())
        }
    }
}