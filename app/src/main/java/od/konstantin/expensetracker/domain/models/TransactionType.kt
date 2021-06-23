package od.konstantin.expensetracker.domain.models

enum class TransactionType(private val prefix: String) {
    INCOME("+"), EXPENSE("-");

    val isIncome: Boolean
        get() = this == INCOME

    val isExpense: Boolean
        get() = this == EXPENSE

    override fun toString(): String {
        return prefix
    }
}