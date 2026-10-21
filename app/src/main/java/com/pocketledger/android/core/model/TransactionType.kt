package com.pocketledger.android.core.model

enum class TransactionType {
    INCOME,
    EXPENSE,
    TRANSFER
}

enum class AccountType {
    CASH,
    BANK,
    DIGITAL_WALLET,
    CREDIT_CARD,
    OTHER
}

enum class CategoryType {
    INCOME,
    EXPENSE
}

enum class PaymentMethod {
    CASH,
    UPI,
    DEBIT_CARD,
    CREDIT_CARD,
    NET_BANKING,
    OTHER
}

enum class RecurrenceFrequency {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
}
