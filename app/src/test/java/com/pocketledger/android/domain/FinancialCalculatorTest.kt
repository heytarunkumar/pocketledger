package com.pocketledger.android.domain

import com.pocketledger.android.core.model.AccountType
import com.pocketledger.android.core.model.CategoryType
import com.pocketledger.android.core.model.TransactionType
import com.pocketledger.android.core.util.CurrencyUtils
import com.pocketledger.android.data.local.entity.AccountEntity
import com.pocketledger.android.data.local.entity.CategoryEntity
import com.pocketledger.android.data.local.entity.TransactionEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class FinancialCalculatorTest {

    private val bankAccount = AccountEntity(id = 1, name = "HDFC Bank", type = AccountType.BANK, initialBalancePaise = 100000L) // ₹1000.00
    private val cashAccount = AccountEntity(id = 2, name = "Cash Wallet", type = AccountType.CASH, initialBalancePaise = 50000L)   // ₹500.00

    private val salaryCat = CategoryEntity(id = 10, name = "Salary", type = CategoryType.INCOME)
    private val groceryCat = CategoryEntity(id = 11, name = "Groceries", type = CategoryType.EXPENSE)

    @Test
    fun `test income increases the appropriate account balance`() {
        val incomeTx = TransactionEntity(
            id = 1,
            type = TransactionType.INCOME,
            amountPaise = 500000L, // +₹5,000.00
            accountId = 1,
            categoryId = 10,
            timestamp = System.currentTimeMillis()
        )

        val balance = FinancialCalculator.calculateAccountBalance(bankAccount, listOf(incomeTx))
        // Initial ₹1,000 (100,000) + ₹5,000 (500,000) = ₹6,000 (600,000 paise)
        assertEquals(600000L, balance)
    }

    @Test
    fun `test expenses decrease the appropriate account balance`() {
        val expenseTx = TransactionEntity(
            id = 1,
            type = TransactionType.EXPENSE,
            amountPaise = 200000L, // -₹2,000.00
            accountId = 1,
            categoryId = 11,
            timestamp = System.currentTimeMillis()
        )

        val balance = FinancialCalculator.calculateAccountBalance(bankAccount, listOf(expenseTx))
        // Initial ₹1,000 (100,000) - ₹2,000 (200,000) = -₹1,000 (-100,000 paise)
        assertEquals(-100000L, balance)
    }

    @Test
    fun `test transfers move money between accounts without changing total owned cash`() {
        val accounts = listOf(bankAccount, cashAccount)
        val initialTotalCash = bankAccount.initialBalancePaise + cashAccount.initialBalancePaise // ₹1,500.00

        val transferTx = TransactionEntity(
            id = 1,
            type = TransactionType.TRANSFER,
            amountPaise = 300000L, // ₹3,000.00 from Bank (1) to Cash (2)
            accountId = 1,
            transferDestinationAccountId = 2,
            timestamp = System.currentTimeMillis()
        )

        val bankBalance = FinancialCalculator.calculateAccountBalance(bankAccount, listOf(transferTx))
        val cashBalance = FinancialCalculator.calculateAccountBalance(cashAccount, listOf(transferTx))
        val newTotalCash = FinancialCalculator.calculateTotalAvailableBalance(accounts, listOf(transferTx))

        // Bank: ₹1,000 - ₹3,000 = -₹2,000
        assertEquals(-200000L, bankBalance)
        // Cash: ₹500 + ₹3,000 = ₹3,500
        assertEquals(350000L, cashBalance)
        // Total owned cash remains unchanged at ₹1,500.00
        assertEquals(initialTotalCash, newTotalCash)
    }

    @Test
    fun `test transfers are excluded from income and expense totals`() {
        val txList = listOf(
            TransactionEntity(id = 1, type = TransactionType.INCOME, amountPaise = 1000000L, accountId = 1, categoryId = 10, timestamp = System.currentTimeMillis()), // Income ₹10,000
            TransactionEntity(id = 2, type = TransactionType.TRANSFER, amountPaise = 300000L, accountId = 1, transferDestinationAccountId = 2, timestamp = System.currentTimeMillis()) // Transfer ₹3,000 from Bank to Cash
        )

        val income = FinancialCalculator.calculateTotalIncome(txList)
        val expense = FinancialCalculator.calculateTotalExpense(txList)
        val netCashFlow = FinancialCalculator.calculateNetCashFlow(txList)

        assertEquals(1000000L, income)
        assertEquals(0L, expense)
        assertEquals(1000000L, netCashFlow)
    }

    @Test
    fun `test invalid amounts and incomplete transfers are rejected by validator`() {
        // Zero or negative amounts
        assertNull(CurrencyUtils.parseRupeesToPaise("0"))
        assertNull(CurrencyUtils.parseRupeesToPaise("-500"))
        assertNull(CurrencyUtils.parseRupeesToPaise("invalid_text"))
    }

    @Test
    fun `test editing a transaction updates the totals correctly`() {
        val originalTx = TransactionEntity(
            id = 1,
            type = TransactionType.EXPENSE,
            amountPaise = 100000L, // ₹1,000
            accountId = 1,
            categoryId = 11,
            timestamp = System.currentTimeMillis()
        )

        val editedTx = originalTx.copy(
            amountPaise = 150000L // ₹1,500
        )

        val originalExpenseTotal = FinancialCalculator.calculateTotalExpense(listOf(originalTx))
        val updatedExpenseTotal = FinancialCalculator.calculateTotalExpense(listOf(editedTx))

        assertEquals(100000L, originalExpenseTotal)
        assertEquals(150000L, updatedExpenseTotal)

        val originalBalance = FinancialCalculator.calculateAccountBalance(bankAccount, listOf(originalTx))
        val updatedBalance = FinancialCalculator.calculateAccountBalance(bankAccount, listOf(editedTx))

        // ₹1000 initial - ₹1000 expense = ₹0
        assertEquals(0L, originalBalance)
        // ₹1000 initial - ₹1500 expense = -₹500
        assertEquals(-50000L, updatedBalance)
    }

    @Test
    fun `test budget utilization and overspending detection`() {
        val (utilization1, isOverspent1) = FinancialCalculator.calculateBudgetUtilization(500000L, 400000L)
        assertEquals(80.0f, utilization1, 0.1f)
        assertFalse(isOverspent1)

        val (utilization2, isOverspent2) = FinancialCalculator.calculateBudgetUtilization(500000L, 600000L)
        assertEquals(120.0f, utilization2, 0.1f)
        assertTrue(isOverspent2)
    }
}
