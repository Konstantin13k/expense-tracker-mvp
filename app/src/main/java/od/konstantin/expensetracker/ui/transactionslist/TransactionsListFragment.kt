package od.konstantin.expensetracker.ui.transactionslist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.ItemTouchHelper
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import od.konstantin.expensetracker.R
import od.konstantin.expensetracker.common.SwipeToDeleteCallback
import od.konstantin.expensetracker.databinding.FragmentTransactionsListBinding
import od.konstantin.expensetracker.di.components.DaggerTransactionsListComponent
import od.konstantin.expensetracker.domain.models.Transaction
import od.konstantin.expensetracker.presenters.transactionslist.TransactionsListPresenter
import od.konstantin.expensetracker.presenters.transactionslist.TransactionsListView
import od.konstantin.expensetracker.ui.transactionslist.transactions_adapter.TransactionsListAdapter
import od.konstantin.expensetracker.utils.extensions.appComponent
import javax.inject.Inject
import javax.inject.Provider

class TransactionsListFragment : MvpAppCompatFragment(R.layout.fragment_transactions_list),
    TransactionsListView {

    @Inject
    lateinit var presenterProvider: Provider<TransactionsListPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    private val binding: FragmentTransactionsListBinding by viewBinding(
        FragmentTransactionsListBinding::bind
    )

    private lateinit var transactionsListAdapter: TransactionsListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerTransactionsListComponent.factory().create(appComponent)
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTransactionsListAdapter()
    }

    override fun showTransactionData(transactionData: PagingData<Transaction>) {
        lifecycleScope.launch {
            transactionsListAdapter.submitData(transactionData)
        }
    }

    private fun initTransactionsListAdapter() {
        transactionsListAdapter = TransactionsListAdapter { transaction ->
            findNavController().navigate(
                TransactionsListFragmentDirections.actionTransactionsListFragmentToTransactionDetailsFragment(
                    transactionId = transaction.transactionId ?: return@TransactionsListAdapter
                )
            )
        }
        binding.transactions.adapter = transactionsListAdapter
        presenter.loadTransactions()
        addSwipeToDelete()
    }

    private fun addSwipeToDelete() {
        val transactions = binding.transactions
        val deletedOneTransactionText = resources.getString(R.string.snackbar_deleted_title, 1)
        val undoDeletedTransactionText = resources.getString(R.string.undo_button_text)

        val swipeToDeleteCallback = SwipeToDeleteCallback(
            recyclerView = transactions,
            deletedOneTransactionText = deletedOneTransactionText,
            undoDeletedTransactionText = undoDeletedTransactionText,
            onSwipe = { position ->
                transactionsListAdapter.peek(position)?.let { transaction ->
                    presenter.deleteTransaction(transaction)
                }
            },
            onUndo = {
                presenter.undoDeletedTransaction()
            }
        )

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(transactions)
    }
}