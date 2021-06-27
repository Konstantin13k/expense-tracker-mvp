package od.konstantin.expensetracker.presenters.dashboard

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import od.konstantin.expensetracker.domain.models.Transaction
import od.konstantin.expensetracker.domain.repositories.TransactionsRepository
import od.konstantin.expensetracker.domain.usecases.DeleteTransaction
import javax.inject.Inject

class DashboardPresenter @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val deleteTransaction: DeleteTransaction,
) : MvpPresenter<DashboardView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadBalanceInfo()
        loadRecentTransactions()
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

    private fun loadBalanceInfo() {
        presenterScope.launch {
            transactionsRepository.observeBalanceInfo().collect { balanceInfo ->
                viewState.showBalanceInfo(balanceInfo)
            }
        }
    }

    private fun loadRecentTransactions() {
        presenterScope.launch {
            transactionsRepository.observeRecentTransactions().collect { transactions ->
                viewState.showTransactions(transactions)
            }
        }
    }
}