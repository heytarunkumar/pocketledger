package com.pocketledger.android.core.util

import java.text.NumberFormat
import java.util.Locale

object CurrencyUtils {

    private val indianLocale = Locale("en", "IN")

    /**
     * Formats integer paise into a human-readable Indian Rupee string.
     * E.g., 12345678L -> "₹1,23,456.78"
     */
    fun formatPaiseToRupees(paise: Long, showSymbol: Boolean = true): String {
        val rupees = paise.toDouble() / 100.0
        val formatter = NumberFormat.getCurrencyInstance(indianLocale).apply {
            maximumFractionDigits = 2
            minimumFractionDigits = 2
        }
        val formatted = formatter.format(rupees)
        return if (!showSymbol) {
            formatted.replace("₹", "").replace("\u00A0", "").trim()
        } else {
            formatted
        }
    }

    /**
     * Parses a string input (e.g. "1250", "1250.50", "1,250.50") into positive Long paise.
     * Returns null if invalid or <= 0.
     */
    fun parseRupeesToPaise(input: String): Long? {
        val cleanInput = input.replace(",", "").replace("₹", "").replace("\u00A0", "").trim()
        if (cleanInput.isEmpty()) return null

        val parts = cleanInput.split(".")
        if (parts.size > 2) return null

        return try {
            val wholePart = parts[0].toLongOrNull() ?: return null
            if (wholePart < 0) return null

            val decimalPart = if (parts.size == 2) {
                val decStr = parts[1]
                when {
                    decStr.length == 0 -> 0L
                    decStr.length == 1 -> (decStr + "0").toLongOrNull() ?: return null
                    decStr.length == 2 -> decStr.toLongOrNull() ?: return null
                    else -> decStr.substring(0, 2).toLongOrNull() ?: return null
                }
            } else {
                0L
            }

            val totalPaise = wholePart * 100L + decimalPart
            if (totalPaise <= 0L) null else totalPaise
        } catch (e: Exception) {
            null
        }
    }
}
