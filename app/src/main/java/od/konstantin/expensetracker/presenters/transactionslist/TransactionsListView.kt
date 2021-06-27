package od.konstantin.expensetracker.presenters.transactionslist

import androidx.paging.PagingData
import moxy.MvpView
import moxy.viewstate.strategy.alias.OneExecution
import od.konstantin.expensetracker.domain.models.Transaction

interface TransactionsListView : MvpView {

    @OneExecution
    fun showTransactionData(transactionData: PagingData<Transaction>)
}