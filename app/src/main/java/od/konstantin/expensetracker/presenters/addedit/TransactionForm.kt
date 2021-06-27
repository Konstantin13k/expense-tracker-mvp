package od.konstantin.expensetracker.presenters.addedit

import od.konstantin.expensetracker.domain.models.TransactionTag
import od.konstantin.expensetracker.domain.models.TransactionType
import java.util.*

data class TransactionForm(
    var transactionId: Int? = null,
    var title: String = "",
    var amount: Double? = null,
    var transactionType: TransactionType? = null,
    var transactionTag: TransactionTag? = null,
    var transactionDate: Date? = null
)