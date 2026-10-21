package com.pocketledger.android.core.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CurrencyUtilsTest {

    @Test
    fun `test parseRupeesToPaise valid inputs`() {
        assertEquals(15000L, CurrencyUtils.parseRupeesToPaise("150"))
        assertEquals(15050L, CurrencyUtils.parseRupeesToPaise("150.5"))
        assertEquals(15050L, CurrencyUtils.parseRupeesToPaise("150.50"))
        assertEquals(125050L, CurrencyUtils.parseRupeesToPaise("1,250.50"))
        assertEquals(125000L, CurrencyUtils.parseRupeesToPaise("₹ 1,250"))
    }

    @Test
    fun `test parseRupeesToPaise invalid or non-positive inputs`() {
        assertNull(CurrencyUtils.parseRupeesToPaise("0"))
        assertNull(CurrencyUtils.parseRupeesToPaise("-100"))
        assertNull(CurrencyUtils.parseRupeesToPaise("abc"))
        assertNull(CurrencyUtils.parseRupeesToPaise("12.34.56"))
    }

    @Test
    fun `test formatPaiseToRupees`() {
        val formattedWithSymbol = CurrencyUtils.formatPaiseToRupees(12345678L, showSymbol = true)
        assertTrue(formattedWithSymbol.contains("123"))
        assertTrue(formattedWithSymbol.contains("456.78"))

        val formattedNoSymbol = CurrencyUtils.formatPaiseToRupees(12345678L, showSymbol = false)
        assertTrue(formattedNoSymbol.contains("456.78"))
    }
}
