package od.konstantin.expensetracker.presenters.details

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import od.konstantin.expensetracker.domain.repositories.TransactionsRepository
import javax.inject.Inject

class TransactionDetailsPresenter @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) : MvpPresenter<TransactionDetailsView>() {

    fun loadTransaction(transactionId: Int) {
        presenterScope.launch {
            transactionsRepository.observeTransaction(transactionId).collect { transaction ->
                viewState.showDetails(transaction)
            }
        }
    }
}