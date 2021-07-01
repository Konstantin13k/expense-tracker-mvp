package od.konstantin.expensetracker.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.transition.platform.Hold
import com.google.android.material.transition.platform.MaterialSharedAxis
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import od.konstantin.expensetracker.R
import od.konstantin.expensetracker.common.SwipeToDeleteCallback
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
        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }
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
            navigateToAddEditTransaction()
        }
        binding.labelSeeAllTransactions.setOnClickListener {
            navigateToTransactionsList()
        }
    }

    private fun initTransactionsAdapter() {
        transactionsAdapter = TransactionsAdapter { transaction ->
            navigateToTransactionDetails(transaction)
        }
        binding.recentTransactions.adapter = transactionsAdapter
        addSwipeToDelete()
    }

    private fun addSwipeToDelete() {
        val recentTransactions = binding.recentTransactions
        val deletedOneTransactionText = resources.getString(R.string.snackbar_deleted_title, 1)
        val undoDeletedTransactionText = resources.getString(R.string.undo_button_text)

        val swipeToDeleteCallback = SwipeToDeleteCallback(
            recyclerView = recentTransactions,
            deletedOneTransactionText = deletedOneTransactionText,
            undoDeletedTransactionText = undoDeletedTransactionText,
            onSwipe = { position ->
                val transaction = transactionsAdapter.currentList[position]
                presenter.deleteTransaction(transaction)
            },
            onUndo = {
                presenter.undoDeletedTransaction()
            }
        )

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recentTransactions)
    }

    private fun navigateToAddEditTransaction() {
        val motionDuration = resources.getInteger(R.integer.shared_element_motion_duration).toLong()
        val extras = FragmentNavigatorExtras(
            binding.addTransaction to resources.getString(R.string.transition_name_add_edit_transaction)
        )

        exitTransition = Hold().apply {
            duration = motionDuration
        }
        reenterTransition = null

        findNavController().navigate(
            DashboardFragmentDirections.actionDashboardFragmentToAddEditFragment(),
            extras
        )
    }

    private fun navigateToTransactionsList() {
        val motionDuration = resources.getInteger(R.integer.shared_axis_motion_duration).toLong()
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            duration = motionDuration
        }
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
            duration = motionDuration
        }

        findNavController().navigate(
            DashboardFragmentDirections.actionDashboardFragmentToTransactionsListFragment(),
        )
    }

    private fun navigateToTransactionDetails(transaction: Transaction) {
        val motionDuration = resources.getInteger(R.integer.shared_axis_motion_duration).toLong()
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).apply {
            duration = motionDuration
        }
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).apply {
            duration = motionDuration
        }

        findNavController().navigate(
            DashboardFragmentDirections.actionDashboardFragmentToTransactionDetailsFragment(
                transactionId = transaction.transactionId ?: return
            )
        )
    }
}