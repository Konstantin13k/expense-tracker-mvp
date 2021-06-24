package od.konstantin.expensetracker.data.local.transactions.dao

import androidx.room.*
import od.konstantin.expensetracker.data.local.transactions.entities.TransactionEntity

@Dao
interface TransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTransaction(transaction: TransactionEntity)

    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions ORDER BY transaction_date LIMIT 20")
    suspend fun getRecentTransactions(): List<TransactionEntity>

    @Query("DELETE FROM transactions WHERE transaction_id = :transactionId")
    suspend fun deleteTransaction(transactionId: Int)

    @Query("DELETE FROM transactions")
    suspend fun deleteAllTransactions()
}