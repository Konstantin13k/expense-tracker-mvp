package od.konstantin.expensetracker.presenters.addedit

data class TransactionFormError(
    var isTitleEmpty: Boolean = false,
    var isAmountEmpty: Boolean = false,
    var isTypeEmpty: Boolean = false,
    var isTagEmpty: Boolean = false,
    var isDateEmpty: Boolean = false
)