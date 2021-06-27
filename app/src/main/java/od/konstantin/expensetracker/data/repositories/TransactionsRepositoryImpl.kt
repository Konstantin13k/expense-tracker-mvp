package od.konstantin.expensetracker.data.repositories

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.withContext
import od.konstantin.expensetracker.data.local.transactions.dao.TransactionsDao
import od.konstantin.expensetracker.data.mappers.BalanceInfoMapper
import od.konstantin.expensetracker.data.mappers.TransactionMapper
import od.konstantin.expensetracker.di.qualifiers.IODispatcher
import od.konstantin.expensetracker.domain.models.BalanceInfo
import od.konstantin.expensetracker.domain.models.Transaction
import od.konstantin.expensetracker.domain.repositories.TransactionsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionsRepositoryImpl @Inject constructor(
    private val transactionsDao: TransactionsDao,
    private val transactionMapper: TransactionMapper,
    private val balanceInfoMapper: BalanceInfoMapper,
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

    override fun observeRecentTransactions(): Flow<List<Transaction>> {
        return transactionsDao.observeRecentTransactions()
            .map { transactions ->
                transactions.map(transactionMapper::fromEntity)
            }
    }

    override fun observeTransaction(transactionId: Int): Flow<Transaction> {
        return transactionsDao.observeTransaction(transactionId)
            .map(transactionMapper::fromEntity)
    }

    override fun observeBalanceInfo(): Flow<BalanceInfo> {
        return transactionsDao.observeTransactionsInfo().transform {
            emit(balanceInfoMapper.fromTransactionsInfo(it))
        }
    }
}