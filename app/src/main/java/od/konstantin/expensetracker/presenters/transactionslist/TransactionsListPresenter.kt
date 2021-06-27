package od.konstantin.expensetracker.presenters.transactionslist

import androidx.paging.cachedIn
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import od.konstantin.expensetracker.domain.models.Transaction
import od.konstantin.expensetracker.domain.repositories.TransactionsRepository
import od.konstantin.expensetracker.domain.usecases.DeleteTransaction
import javax.inject.Inject

private const val DEFAULT_PAGE_SIZE = 30

class TransactionsListPresenter @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val deleteTransaction: DeleteTransaction
) : MvpPresenter<TransactionsListView>() {

    fun loadTransactions() {
        presenterScope.launch {
            transactionsRepository.observeTransactions(DEFAULT_PAGE_SIZE)
                .cachedIn(presenterScope)
                .distinctUntilChanged()
                .collectLatest { transactionData ->
                    viewState.showTransactionData(transactionData)
                }
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        presenterScope.launch {
            deleteTransaction.deleteTransaction(transaction)
        }
    }

    fun undoDeletedTransaction() {
        presenterScope.launch {
            deleteTransaction.undoDeletedTransaction()
        }
    }
}