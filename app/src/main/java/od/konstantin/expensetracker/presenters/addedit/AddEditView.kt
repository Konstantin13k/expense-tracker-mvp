package od.konstantin.expensetracker.presenters.addedit

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

interface AddEditView : MvpView {

    @AddToEndSingle
    fun showTransactionFormError(formError: TransactionFormError)

    @OneExecution
    fun showTransactionForm(form: TransactionForm)

    @OneExecution
    fun navigateToPreviousFragment()
}