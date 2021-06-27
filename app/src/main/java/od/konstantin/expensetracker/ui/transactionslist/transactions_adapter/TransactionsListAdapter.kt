package od.konstantin.expensetracker.ui.transactionslist.transactions_adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import od.konstantin.expensetracker.common.diffutils.TransactionDiffUtil
import od.konstantin.expensetracker.common.viewholders.TransactionViewHolder
import od.konstantin.expensetracker.domain.models.Transaction

class TransactionsListAdapter(
    private val onSelectTransaction: (Transaction) -> Unit
) :
    PagingDataAdapter<Transaction, TransactionViewHolder>(TransactionDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        getItem(position)?.let { transaction ->
            holder.bind(transaction)
            holder.itemView.setOnClickListener {
                onSelectTransaction(transaction)
            }
        }
    }
}