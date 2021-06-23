package od.konstantin.expensetracker.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import od.konstantin.expensetracker.R
import od.konstantin.expensetracker.databinding.FragmentDashboardBinding
import od.konstantin.expensetracker.ui.dashboard.transactions_adapter.TransactionsAdapter


class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private val binding: FragmentDashboardBinding by viewBinding(FragmentDashboardBinding::bind)

    private lateinit var transactionsAdapter: TransactionsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTransactionsAdapter()
    }

    private fun initTransactionsAdapter() {
        transactionsAdapter = TransactionsAdapter()
        binding.recentTransactions.adapter = transactionsAdapter
    }
}