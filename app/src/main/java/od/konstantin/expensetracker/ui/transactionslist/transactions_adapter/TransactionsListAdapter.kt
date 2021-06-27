package od.konstantin.expensetracker.ui.transactionslist.transactions_adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import od.konstantin.expensetracker.domain.models.Transaction
import od.konstantin.expensetracker.ui.dashboard.transactions_adapter.TransactionDiffUtil
import od.konstantin.expensetracker.ui.dashboard.transactions_adapter.TransactionViewHolder

class TransactionsListAdapter :
    PagingDataAdapter<Transaction, TransactionViewHolder>(TransactionDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        getItem(position)?.let(holder::bind)
    }
}