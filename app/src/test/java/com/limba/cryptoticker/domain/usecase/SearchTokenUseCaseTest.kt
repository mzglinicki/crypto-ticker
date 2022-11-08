package com.limba.cryptoticker.domain.usecase

import com.limba.cryptoticker.utils.TestDataFactory.createTokenCurrentPriceData
import com.limba.cryptoticker.domain.utils.SupportedToken.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class SearchTokenUseCaseTest {

    private val searchToken = SearchTokenUseCase()

    private val bitcoinTokenData = createTokenCurrentPriceData(BITCOIN)
    private val bitpandaTokenData = createTokenCurrentPriceData(BITPANDA_ECOSYSTEM)
    private val swissBorgTokenData = createTokenCurrentPriceData(SWISS_BORG)

    @Test
    fun `WHEN search text is blank THEN full list is returned`() {
        val text = "  "
        val dataToSearch = listOf(bitcoinTokenData, bitpandaTokenData)

        val result = searchToken(dataToSearch, text)

        assertEquals(dataToSearch, result)
    }

    @Test
    fun `WHEN few tokens names contain provided text THEN all of them are returned`() {
        val text = "bit"
        val dataToSearch = listOf(bitcoinTokenData, bitpandaTokenData, swissBorgTokenData)
        val expectedResult = listOf(bitcoinTokenData, bitpandaTokenData)

        val result = searchToken(dataToSearch, text)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `WHEN tokens names do not contain provided text THEN empty list is returned`() {
        val text = "zzzz"
        val dataToSearch = listOf(bitcoinTokenData, bitpandaTokenData, swissBorgTokenData)

        val result = searchToken(dataToSearch, text)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `WHEN provided text is lowercase THEN the results should be the same as with uppercase text`() {
        val lowercaseText = "swiss"
        val uppercaseText = "SWISS"
        val dataToSearch = listOf(bitcoinTokenData, bitpandaTokenData, swissBorgTokenData)
        val lowerCaseTextResult = searchToken(dataToSearch, lowercaseText)

        val upperCaseTextResult = searchToken(dataToSearch, uppercaseText)

        assertEquals(lowerCaseTextResult, upperCaseTextResult)
    }
}