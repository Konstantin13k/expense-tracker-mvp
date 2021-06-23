package od.konstantin.expensetracker.domain.models

enum class TransactionType {
    INCOME, EXPENSE;

    val isIncome: Boolean
        get() = this == INCOME

    val isExpense: Boolean
        get() = this == EXPENSE
}