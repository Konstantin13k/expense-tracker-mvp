package od.konstantin.expensetracker.data.local.transactions.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import od.konstantin.expensetracker.data.local.transactions.entities.TransactionEntity
import od.konstantin.expensetracker.data.local.transactions.entities.TransactionsInfo

@Dao
interface TransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTransaction(transaction: TransactionEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTransaction(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions ORDER BY transaction_date DESC LIMIT 20")
    suspend fun getRecentTransactions(): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE transaction_id = :transactionId")
    fun observeTransaction(transactionId: Int): Flow<TransactionEntity>

    @Query("SELECT transaction_type_id as transactionTypeId, SUM(transaction_amount) as total FROM transactions GROUP BY transaction_type_id")
    fun observeTransactionsInfo(): Flow<List<TransactionsInfo>>

    @Query("DELETE FROM transactions WHERE transaction_id = :transactionId")
    suspend fun deleteTransaction(transactionId: Int)

    @Query("DELETE FROM transactions")
    suspend fun deleteAllTransactions()
}