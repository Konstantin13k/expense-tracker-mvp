package od.konstantin.expensetracker.ui.dashboard.transactions_adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import od.konstantin.expensetracker.domain.models.Transaction

class TransactionsAdapter(
    private val selectTransaction: (Transaction) -> Unit
) : ListAdapter<Transaction, TransactionViewHolder>(TransactionDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction: Transaction = getItem(position)
        holder.bind(transaction)
        holder.itemView.setOnClickListener {
            selectTransaction(transaction)
        }
    }
}