package od.konstantin.expensetracker.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import od.konstantin.expensetracker.R
import od.konstantin.expensetracker.databinding.FragmentDashboardBinding
import od.konstantin.expensetracker.di.components.DaggerDashboardComponent
import od.konstantin.expensetracker.domain.models.BalanceInfo
import od.konstantin.expensetracker.domain.models.Transaction
import od.konstantin.expensetracker.presenters.dashboard.DashboardPresenter
import od.konstantin.expensetracker.presenters.dashboard.DashboardView
import od.konstantin.expensetracker.ui.dashboard.transactions_adapter.TransactionsAdapter
import od.konstantin.expensetracker.utils.extensions.appComponent
import od.konstantin.expensetracker.utils.extensions.format
import javax.inject.Inject
import javax.inject.Provider


class DashboardFragment : MvpAppCompatFragment(R.layout.fragment_dashboard), DashboardView {

    @Inject
    lateinit var presenterProvider: Provider<DashboardPresenter>

    private val presenter: DashboardPresenter by moxyPresenter { presenterProvider.get() }

    private val binding: FragmentDashboardBinding by viewBinding(FragmentDashboardBinding::bind)

    private lateinit var transactionsAdapter: TransactionsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerDashboardComponent.factory().create(appComponent)
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTransactionsAdapter()
        initListeners()
    }

    override fun showBalanceInfo(balanceInfo: BalanceInfo): Unit = with(binding) {
        totalBalanceView.totalBalance.text = balanceInfo.balance.format()
        totalIncomeView.totalIncome.text = balanceInfo.totalIncome.format(prefix = "+")
        totalExpenseView.totalExpense.text = balanceInfo.totalExpense.format(prefix = "-")
    }

    override fun showTransactions(transactions: List<Transaction>) {
        transactionsAdapter.submitList(transactions)
    }

    private fun initListeners() {
        binding.addTransaction.setOnClickListener {
            findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToAddEditFragment()
            )
        }
    }

    private fun initTransactionsAdapter() {
        transactionsAdapter = TransactionsAdapter { transaction ->
            findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToTransactionDetailsFragment(
                    transactionId = transaction.transactionId ?: return@TransactionsAdapter
                )
            )
        }
        binding.recentTransactions.adapter = transactionsAdapter
        addSwipeToDelete()
    }

    private fun addSwipeToDelete() {
        val recentTransactions = binding.recentTransactions
        val deletedOneTransactionText = resources.getString(R.string.snackbar_deleted_title, 1)
        val undoDeletedTransactionText = resources.getString(R.string.undo_button_text)

        val swipeToDeleteCallback = object : SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val transaction = transactionsAdapter.currentList[position]
                presenter.deleteTransaction(transaction)

                Snackbar.make(recentTransactions, deletedOneTransactionText, Snackbar.LENGTH_LONG)
                    .setAction(undoDeletedTransactionText) {
                        presenter.undoDeletedTransaction()
                    }.show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.recentTransactions)
    }
}