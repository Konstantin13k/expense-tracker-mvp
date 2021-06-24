package od.konstantin.expensetracker.data.repositories

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import od.konstantin.expensetracker.data.local.transactions.dao.TransactionsDao
import od.konstantin.expensetracker.data.mappers.TransactionMapper
import od.konstantin.expensetracker.di.qualifiers.IODispatcher
import od.konstantin.expensetracker.domain.models.Transaction
import od.konstantin.expensetracker.domain.repositories.TransactionsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionsRepositoryImpl @Inject constructor(
    private val transactionsDao: TransactionsDao,
    private val transactionMapper: TransactionMapper,
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher
) : TransactionsRepository {

    override suspend fun addTransaction(transaction: Transaction) {
        withContext(ioDispatcher) {
            val transactionEntity = transactionMapper.toEntity(transaction)
            transactionsDao.addTransaction(transactionEntity)
        }
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        withContext(ioDispatcher) {
            val transactionEntity = transactionMapper.toEntity(transaction)
            transactionsDao.updateTransaction(transactionEntity)
        }
    }

    override suspend fun getRecentTransactions(): List<Transaction> {
        return withContext(ioDispatcher) {
            transactionsDao.getRecentTransactions()
                .map(transactionMapper::fromEntity)
        }
    }
}