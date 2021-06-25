package od.konstantin.expensetracker.presenters.dashboard

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import od.konstantin.expensetracker.domain.models.BalanceInfo
import od.konstantin.expensetracker.domain.models.Transaction

interface DashboardView : MvpView {

    @AddToEndSingle
    fun showTransactions(transactions: List<Transaction>)

    @AddToEndSingle
    fun showBalanceInfo(balanceInfo: BalanceInfo)
}