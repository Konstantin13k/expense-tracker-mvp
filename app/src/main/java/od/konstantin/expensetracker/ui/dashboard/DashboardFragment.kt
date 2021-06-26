package od.konstantin.expensetracker.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
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
        presenter.loadRecentTransactions()
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
    }
}