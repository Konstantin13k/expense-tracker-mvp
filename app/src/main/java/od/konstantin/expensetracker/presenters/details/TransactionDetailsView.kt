package od.konstantin.expensetracker.presenters.details

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import od.konstantin.expensetracker.domain.models.Transaction

interface TransactionDetailsView : MvpView {

    @AddToEndSingle
    fun showDetails(transaction: Transaction)
}