package od.konstantin.expensetracker.domain.repositories

import kotlinx.coroutines.flow.Flow
import od.konstantin.expensetracker.domain.models.BalanceInfo
import od.konstantin.expensetracker.domain.models.Transaction

interface TransactionsRepository {

    suspend fun addTransaction(transaction: Transaction)

    suspend fun updateTransaction(transaction: Transaction)

    fun observeRecentTransactions(): Flow<List<Transaction>>

    fun observeTransaction(transactionId: Int): Flow<Transaction>

    fun observeBalanceInfo(): Flow<BalanceInfo>
}