package od.konstantin.expensetracker.presenters.addedit

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface AddEditView : MvpView {

    @AddToEndSingle
    fun showTransactionFormError(formError: TransactionFormError)
}