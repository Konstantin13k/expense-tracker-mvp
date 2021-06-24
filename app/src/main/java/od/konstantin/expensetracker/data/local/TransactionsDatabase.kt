package od.konstantin.expensetracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import od.konstantin.expensetracker.data.local.transactions.dao.TransactionsDao
import od.konstantin.expensetracker.data.local.transactions.entities.TransactionEntity

@Database(
    entities = [TransactionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TransactionsDatabase : RoomDatabase() {

    abstract val transactionsDao: TransactionsDao
}