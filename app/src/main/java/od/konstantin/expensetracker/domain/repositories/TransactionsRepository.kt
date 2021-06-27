package od.konstantin.expensetracker.domain.repositories

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import od.konstantin.expensetracker.domain.models.BalanceInfo
import od.konstantin.expensetracker.domain.models.Transaction

interface TransactionsRepository {

    suspend fun addTransaction(transaction: Transaction)

    suspend fun updateTransaction(transaction: Transaction)

    suspend fun deleteTransaction(transactionId: Int)

    fun observeRecentTransactions(): Flow<List<Transaction>>

    fun observeTransactions(pageSize: Int): Flow<PagingData<Transaction>>

    fun observeTransaction(transactionId: Int): Flow<Transaction>

    fun observeBalanceInfo(): Flow<BalanceInfo>
}