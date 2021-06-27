package od.konstantin.expensetracker.presenters.transactionslist

import androidx.paging.cachedIn
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import od.konstantin.expensetracker.domain.repositories.TransactionsRepository

private const val DEFAULT_PAGE_SIZE = 30

class TransactionsPresenter(
    private val transactionsRepository: TransactionsRepository
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
}