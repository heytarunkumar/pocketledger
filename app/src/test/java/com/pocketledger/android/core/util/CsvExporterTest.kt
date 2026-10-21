package com.pocketledger.android.core.util

import com.pocketledger.android.core.model.AccountType
import com.pocketledger.android.core.model.CategoryType
import com.pocketledger.android.core.model.PaymentMethod
import com.pocketledger.android.core.model.TransactionType
import com.pocketledger.android.data.local.entity.AccountEntity
import com.pocketledger.android.data.local.entity.CategoryEntity
import com.pocketledger.android.data.local.entity.TransactionEntity
import com.pocketledger.android.data.local.entity.TransactionWithDetails
import org.junit.Assert.assertTrue
import org.junit.Test

class CsvExporterTest {

    @Test
    fun `test exportTransactionsToCsv outputs valid csv content`() {
        val account = AccountEntity(id = 1, name = "Bank Account", type = AccountType.BANK)
        val category = CategoryEntity(id = 2, name = "Groceries", type = CategoryType.EXPENSE)
        val transaction = TransactionEntity(
            id = 100,
            type = TransactionType.EXPENSE,
            amountPaise = 15050L, // ₹150.50
            timestamp = 1700000000000L,
            note = "Supermarket shopping",
            accountId = 1,
            categoryId = 2,
            paymentMethod = PaymentMethod.UPI
        )

        val txDetails = TransactionWithDetails(
            transaction = transaction,
            account = account,
            destinationAccount = null,
            category = category
        )

        val csvOutput = CsvExporter.exportTransactionsToCsv(listOf(txDetails))

        assertTrue(csvOutput.contains("ID,Date,Type,Amount (INR),Account,Destination Account,Category,Payment Method,Note,Tags"))
        assertTrue(csvOutput.contains("EXPENSE"))
        assertTrue(csvOutput.contains("150.50"))
        assertTrue(csvOutput.contains("Bank Account"))
        assertTrue(csvOutput.contains("Groceries"))
        assertTrue(csvOutput.contains("Supermarket shopping"))
    }
}
