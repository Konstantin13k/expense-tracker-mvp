package od.konstantin.expensetracker.presenters.dashboard

import android.util.Log
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import od.konstantin.expensetracker.domain.models.Transaction
import od.konstantin.expensetracker.domain.repositories.TransactionsRepository
import javax.inject.Inject

class DashboardPresenter @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) : MvpPresenter<DashboardView>() {

    private var recentlyDeletedTransaction: Transaction? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadBalanceInfo()
        loadRecentTransactions()
    }

    fun deleteTransaction(transaction: Transaction) {
        recentlyDeletedTransaction = transaction
        presenterScope.launch {
            transaction.transactionId?.let { transactionId ->
                transactionsRepository.deleteTransaction(transactionId)
            }
        }
    }

    fun undoDeletedTransaction() {
        presenterScope.launch {
            recentlyDeletedTransaction?.let { transaction ->
                transactionsRepository.addTransaction(transaction)
            }
            recentlyDeletedTransaction = null
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