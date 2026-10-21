package com.pocketledger.android.core.util

import com.pocketledger.android.data.local.entity.TransactionWithDetails
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object CsvExporter {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    /**
     * Converts a list of TransactionWithDetails into standard CSV format.
     */
    fun exportTransactionsToCsv(transactions: List<TransactionWithDetails>): String {
        val sb = StringBuilder()
        // Header
        sb.append("ID,Date,Type,Amount (INR),Account,Destination Account,Category,Payment Method,Note,Tags\n")

        for (item in transactions) {
            val tx = item.transaction
            val dateStr = dateFormat.format(Date(tx.timestamp))
            val amountRupees = tx.amountPaise.toDouble() / 100.0
            val accountName = escapeCsv(item.account.name)
            val destAccountName = escapeCsv(item.destinationAccount?.name ?: "")
            val categoryName = escapeCsv(item.category?.name ?: "")
            val note = escapeCsv(tx.note)
            val tags = escapeCsv(tx.tags)

            sb.append("${tx.id},")
                .append("\"$dateStr\",")
                .append("${tx.type.name},")
                .append(String.format(Locale.US, "%.2f", amountRupees)).append(",")
                .append("\"$accountName\",")
                .append("\"$destAccountName\",")
                .append("\"$categoryName\",")
                .append("${tx.paymentMethod.name},")
                .append("\"$note\",")
                .append("\"$tags\"\n")
        }

        return sb.toString()
    }

    private fun escapeCsv(value: String): String {
        return value.replace("\"", "\"\"")
    }
}
