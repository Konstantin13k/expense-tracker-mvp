package od.konstantin.expensetracker.presenters.dashboard

import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import od.konstantin.expensetracker.domain.repositories.TransactionsRepository
import javax.inject.Inject

class DashboardPresenter @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) : MvpPresenter<DashboardView>() {

    fun loadRecentTransactions() {
        presenterScope.launch {
            val transactions = transactionsRepository.getRecentTransactions()
            viewState.showTransactions(transactions)
        }
    }
}