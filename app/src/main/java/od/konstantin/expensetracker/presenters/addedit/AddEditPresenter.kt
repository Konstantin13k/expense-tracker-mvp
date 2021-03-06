package od.konstantin.expensetracker.presenters.addedit

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import od.konstantin.expensetracker.domain.models.Transaction
import od.konstantin.expensetracker.domain.models.TransactionTag
import od.konstantin.expensetracker.domain.models.TransactionType
import od.konstantin.expensetracker.domain.repositories.TransactionsRepository
import java.util.*
import javax.inject.Inject

class AddEditPresenter @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) : MvpPresenter<AddEditView>() {

    private val transactionForm: TransactionForm = TransactionForm()
    private val formError = TransactionFormError()

    fun loadTransactionToEdit(transactionId: Int) {
        presenterScope.launch {
            transactionsRepository.observeTransaction(transactionId)
                .take(1)
                .collect { (title, amount, type, tag, date) ->
                    transactionForm.transactionId = transactionId
                    transactionForm.title = title
                    transactionForm.amount = amount
                    transactionForm.transactionType = type
                    transactionForm.transactionTag = tag
                    transactionForm.transactionDate = date
                    viewState.showTransactionForm(transactionForm)
                }
        }
    }

    fun setTransactionTitle(title: String) {
        transactionForm.title = title
        formError.isTitleEmpty = title.isEmpty()
        if (title.isNotEmpty()) {
            showFormError()
        }
    }

    fun setTransactionAmount(amount: String) {
        transactionForm.amount = amount.toDoubleOrNull()
        formError.isAmountEmpty = transactionForm.amount == null
        if (amount.isNotEmpty()) {
            showFormError()
        }
    }

    fun setTransactionType(type: TransactionType) {
        transactionForm.transactionType = type
        formError.isTypeEmpty = false
        showFormError()
    }

    fun setTransactionTag(tag: TransactionTag) {
        transactionForm.transactionTag = tag
        formError.isTagEmpty = false
        showFormError()
    }

    fun setTransactionDate(date: Date) {
        transactionForm.transactionDate = date
        formError.isDateEmpty = false
        showFormError()
    }

    fun saveButtonPressed() {
        val title = transactionForm.title
        val amount = transactionForm.amount
        val type = transactionForm.transactionType
        val tag = transactionForm.transactionTag
        val date = transactionForm.transactionDate

        if (title.isEmpty()) {
            formError.isTitleEmpty = true
            showFormError()
            return
        }
        if (amount == null) {
            formError.isAmountEmpty = true
            showFormError()
            return
        }
        if (type == null) {
            formError.isTypeEmpty = true
            showFormError()
            return
        }
        if (tag == null) {
            formError.isTagEmpty = true
            showFormError()
            return
        }
        if (date == null) {
            formError.isDateEmpty = true
            showFormError()
            return
        }
        saveTransaction(
            Transaction(title, amount, type, tag, date, Date(), transactionForm.transactionId)
        )
    }

    private fun saveTransaction(transaction: Transaction) {
        presenterScope.launch {
            if (transaction.transactionId == null) {
                transactionsRepository.addTransaction(transaction)
            } else {
                transactionsRepository.updateTransaction(transaction)
            }
            viewState.navigateToPreviousFragment()
        }
    }

    private fun showFormError() {
        viewState.showTransactionFormError(formError)
    }
}