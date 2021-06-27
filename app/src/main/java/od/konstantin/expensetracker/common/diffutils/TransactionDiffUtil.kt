package od.konstantin.expensetracker.common.diffutils

import androidx.recyclerview.widget.DiffUtil
import od.konstantin.expensetracker.domain.models.Transaction

class TransactionDiffUtil : DiffUtil.ItemCallback<Transaction>() {
    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem.transactionId == newItem.transactionId
    }

    override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem == newItem
    }
}