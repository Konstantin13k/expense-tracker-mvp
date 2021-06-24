package od.konstantin.expensetracker.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import od.konstantin.expensetracker.data.local.TransactionsDatabase
import od.konstantin.expensetracker.data.local.transactions.dao.TransactionsDao
import javax.inject.Singleton

private const val DATABASE_NAME = "transactions.db"

@Module
class TransactionsDatabaseModule {

    @Singleton
    @Provides
    fun provideTransactionsDatabase(context: Context): TransactionsDatabase {
        return Room.databaseBuilder(
            context,
            TransactionsDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideTransactionsDao(database: TransactionsDatabase): TransactionsDao {
        return database.transactionsDao
    }
}