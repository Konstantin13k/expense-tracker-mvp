package od.konstantin.expensetracker.domain.repositories

import od.konstantin.expensetracker.domain.models.Transaction

interface TransactionsRepository {

    suspend fun addTransaction(transaction: Transaction)

    suspend fun updateTransaction(transaction: Transaction)

    suspend fun getRecentTransactions(): List<Transaction>
}