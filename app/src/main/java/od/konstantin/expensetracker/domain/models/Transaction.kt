package od.konstantin.expensetracker.domain.models

import java.util.*

data class Transaction(
    val title: String,
    val amount: Double,
    val type: TransactionType,
    val tag: TransactionTag,
    val date: Date,
    val createdAt: Date,
    val transactionId: Int? = null,
)