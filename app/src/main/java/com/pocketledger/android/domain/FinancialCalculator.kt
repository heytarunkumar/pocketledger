package com.pocketledger.android.domain

import com.pocketledger.android.core.model.TransactionType
import com.pocketledger.android.data.local.entity.AccountEntity
import com.pocketledger.android.data.local.entity.CategoryEntity
import com.pocketledger.android.data.local.entity.TransactionEntity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class CategoryPercentageModel(
    val category: CategoryEntity,
    val spentPaise: Long,
    val percentageShare: Float
)

data class MonthlyTrendModel(
    val monthName: String,
    val year: Int,
    val month: Int,
    val incomePaise: Long,
    val expensePaise: Long,
    val netCashFlowPaise: Long
)

object FinancialCalculator {

    /**
     * Calculates current balance for a single account in paise.
     * Balance = initialBalance + sum(INCOME) - sum(EXPENSE) - sum(TRANSFER_OUT) + sum(TRANSFER_IN)
     */
    fun calculateAccountBalance(account: AccountEntity, transactions: List<TransactionEntity>): Long {
        var balance = account.initialBalancePaise

        for (tx in transactions) {
            when (tx.type) {
                TransactionType.INCOME -> {
                    if (tx.accountId == account.id) {
                        balance += tx.amountPaise
                    }
                }
                TransactionType.EXPENSE -> {
                    if (tx.accountId == account.id) {
                        balance -= tx.amountPaise
                    }
                }
                TransactionType.TRANSFER -> {
                    if (tx.accountId == account.id) {
                        balance -= tx.amountPaise
                    }
                    if (tx.transferDestinationAccountId == account.id) {
                        balance += tx.amountPaise
                    }
                }
            }
        }

        return balance
    }

    /**
     * Calculates total available balance across all accounts not excluded from net worth.
     */
    fun calculateTotalAvailableBalance(
        accounts: List<AccountEntity>,
        transactions: List<TransactionEntity>
    ): Long {
        val eligibleAccounts = accounts.filter { !it.isExcludedFromNetWorth }
        return eligibleAccounts.sumOf { calculateAccountBalance(it, transactions) }
    }

    /**
     * Calculates total income for a period.
     * Strictly excludes transfers.
     */
    fun calculateTotalIncome(transactions: List<TransactionEntity>): Long {
        return transactions
            .filter { it.type == TransactionType.INCOME }
            .sumOf { it.amountPaise }
    }

    /**
     * Calculates total expense for a period.
     * Strictly excludes transfers.
     */
    fun calculateTotalExpense(transactions: List<TransactionEntity>): Long {
        return transactions
            .filter { it.type == TransactionType.EXPENSE }
            .sumOf { it.amountPaise }
    }

    /**
     * Calculates net cash flow for a period.
     * Net Cash Flow = Total Income - Total Expense
     */
    fun calculateNetCashFlow(transactions: List<TransactionEntity>): Long {
        return calculateTotalIncome(transactions) - calculateTotalExpense(transactions)
    }

    /**
     * Calculates savings rate percentage for a period.
     * Savings Rate = (Net Cash Flow / Total Income) * 100
     * Returns 0.0 if Total Income is 0.
     */
    fun calculateSavingsRate(transactions: List<TransactionEntity>): Double {
        val income = calculateTotalIncome(transactions)
        if (income <= 0) return 0.0
        val netCashFlow = calculateNetCashFlow(transactions)
        return (netCashFlow.toDouble() / income.toDouble()) * 100.0
    }

    /**
     * Calculates budget utilization percentage and whether the budget is overspent.
     * Returns Pair(utilizationPercentage, isOverspent)
     */
    fun calculateBudgetUtilization(budgetAmountPaise: Long, spentAmountPaise: Long): Pair<Float, Boolean> {
        if (budgetAmountPaise <= 0) return Pair(0f, false)
        val percentage = (spentAmountPaise.toFloat() / budgetAmountPaise.toFloat()) * 100f
        val isOverspent = spentAmountPaise > budgetAmountPaise
        return Pair(percentage, isOverspent)
    }

    /**
     * Group expenses by category and sum paise per category.
     */
    fun calculateCategoryBreakdown(
        transactions: List<TransactionEntity>,
        categories: List<CategoryEntity>
    ): Map<CategoryEntity, Long> {
        val categoryMap = categories.associateBy { it.id }
        val expenseTx = transactions.filter { it.type == TransactionType.EXPENSE && it.categoryId != null }

        val result = mutableMapOf<CategoryEntity, Long>()
        for (tx in expenseTx) {
            val cat = categoryMap[tx.categoryId] ?: continue
            val currentSum = result.getOrDefault(cat, 0L)
            result[cat] = currentSum + tx.amountPaise
        }
        return result
    }

    /**
     * Calculates percentage share per spending category.
     */
    fun calculateCategoryPercentages(
        transactions: List<TransactionEntity>,
        categories: List<CategoryEntity>
    ): List<CategoryPercentageModel> {
        val breakdown = calculateCategoryBreakdown(transactions, categories)
        val totalExpense = breakdown.values.sum()
        if (totalExpense <= 0L) return emptyList()

        return breakdown.map { (cat, spent) ->
            val share = (spent.toFloat() / totalExpense.toFloat()) * 100f
            CategoryPercentageModel(category = cat, spentPaise = spent, percentageShare = share)
        }.sortedByDescending { it.spentPaise }
    }

    /**
     * Calculates month-over-month trend for the last N months.
     */
    fun calculateMonthlyTrend(
        transactions: List<TransactionEntity>,
        monthsCount: Int = 6
    ): List<MonthlyTrendModel> {
        val list = mutableListOf<MonthlyTrendModel>()
        val monthFormat = SimpleDateFormat("MMM yyyy", Locale.getDefault())

        val calendar = Calendar.getInstance()
        for (i in 0 until monthsCount) {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1

            // Calculate range
            val monthStart = calendar.apply {
                set(Calendar.DAY_OF_MONTH, 1)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis

            val monthEnd = calendar.apply {
                set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
                set(Calendar.HOUR_OF_DAY, 23)
                set(Calendar.MINUTE, 59)
                set(Calendar.SECOND, 59)
                set(Calendar.MILLISECOND, 999)
            }.timeInMillis

            val txForMonth = transactions.filter { it.timestamp in monthStart..monthEnd }
            val income = calculateTotalIncome(txForMonth)
            val expense = calculateTotalExpense(txForMonth)
            val net = income - expense

            val monthLabel = monthFormat.format(Date(monthStart))
            list.add(
                MonthlyTrendModel(
                    monthName = monthLabel,
                    year = year,
                    month = month,
                    incomePaise = income,
                    expensePaise = expense,
                    netCashFlowPaise = net
                )
            )

            // Move to previous month
            calendar.timeInMillis = monthStart
            calendar.add(Calendar.MONTH, -1)
        }

        return list.reversed()
    }
}
