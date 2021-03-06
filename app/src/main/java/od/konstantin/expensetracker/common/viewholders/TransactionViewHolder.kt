package od.konstantin.expensetracker.common.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import od.konstantin.expensetracker.databinding.ViewHolderTransactionBinding
import od.konstantin.expensetracker.domain.models.Transaction
import od.konstantin.expensetracker.utils.extensions.format
import od.konstantin.expensetracker.utils.extensions.getColor
import od.konstantin.expensetracker.utils.extensions.getIcon

class TransactionViewHolder private constructor(
    private val binding: ViewHolderTransactionBinding
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(transaction: Transaction): Unit = with(binding) {
        transactionTitle.text = transaction.title
        transactionDate.text = transaction.date.format(itemView.context)
        transactionAmount.setTextColor(transaction.type.getColor(itemView.resources))
        transactionAmount.text = transaction.amount.format(transaction.type.toString())
        transactionIcon.setImageDrawable(transaction.tag.getIcon(itemView.resources))
    }

    companion object {
        fun from(parent: ViewGroup): TransactionViewHolder {
            return TransactionViewHolder(
                ViewHolderTransactionBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }
}