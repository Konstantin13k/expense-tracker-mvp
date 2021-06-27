package od.konstantin.expensetracker.domain.usecases

import od.konstantin.expensetracker.domain.models.Transaction
import od.konstantin.expensetracker.domain.repositories.TransactionsRepository
import javax.inject.Inject

class DeleteTransaction @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) {

    private var lastDeletedTransaction: Transaction? = null

    suspend fun deleteTransaction(transaction: Transaction) {
        lastDeletedTransaction = transaction
        transaction.transactionId?.let { transactionId ->
            transactionsRepository.deleteTransaction(transactionId)
        }
    }

    suspend fun undoDeletedTransaction() {
        lastDeletedTransaction?.let { transaction ->
            transactionsRepository.addTransaction(transaction)
        }
        lastDeletedTransaction = null
    }
}