package od.konstantin.expensetracker.presenters.dashboard

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import od.konstantin.expensetracker.domain.repositories.TransactionsRepository
import javax.inject.Inject

class DashboardPresenter @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) : MvpPresenter<DashboardView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        presenterScope.launch {
            transactionsRepository.observeBalanceInfo().collect { balanceInfo ->
                viewState.showBalanceInfo(balanceInfo)
            }
        }
    }

    fun loadRecentTransactions() {
        presenterScope.launch {
            val transactions = transactionsRepository.getRecentTransactions()
            viewState.showTransactions(transactions)
        }
    }
}