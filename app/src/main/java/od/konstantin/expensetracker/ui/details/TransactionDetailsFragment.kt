package od.konstantin.expensetracker.ui.details

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.transition.platform.Hold
import com.google.android.material.transition.platform.MaterialSharedAxis
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import od.konstantin.expensetracker.R
import od.konstantin.expensetracker.databinding.FragmentTransactionDetailsBinding
import od.konstantin.expensetracker.di.components.DaggerDetailsComponent
import od.konstantin.expensetracker.domain.models.Transaction
import od.konstantin.expensetracker.presenters.details.TransactionDetailsPresenter
import od.konstantin.expensetracker.presenters.details.TransactionDetailsView
import od.konstantin.expensetracker.utils.extensions.appComponent
import od.konstantin.expensetracker.utils.extensions.format
import javax.inject.Inject
import javax.inject.Provider

class TransactionDetailsFragment : MvpAppCompatFragment(R.layout.fragment_transaction_details),
    TransactionDetailsView {

    @Inject
    lateinit var presenterProvider: Provider<TransactionDetailsPresenter>

    private val presenter: TransactionDetailsPresenter by moxyPresenter { presenterProvider.get() }

    private val binding: FragmentTransactionDetailsBinding by viewBinding(
        FragmentTransactionDetailsBinding::bind
    )

    private val args: TransactionDetailsFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerDetailsComponent.factory().create(appComponent)
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        if (savedInstanceState == null) {
            initTransitions()
            presenter.loadTransaction(args.transactionId)
        }
    }

    override fun showDetails(transaction: Transaction): Unit = with(binding) {
        transactionTitle.text = transaction.title
        transactionAmount.text = transaction.amount.format()
        transactionDate.text = transaction.date.format(requireContext())

        transactionType.text =
            resources.getStringArray(R.array.transaction_types)[transaction.type.ordinal]
        transactionTag.text =
            resources.getStringArray(R.array.transaction_tags)[transaction.tag.ordinal]
    }

    private fun initListeners() {
        binding.editTransaction.setOnClickListener {
            navigateToAddEditTransaction()
        }
    }

    private fun initTransitions() {
        val motionDuration = resources.getInteger(R.integer.shared_axis_motion_duration).toLong()
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).apply {
            duration = motionDuration
        }
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).apply {
            duration = motionDuration
        }
    }

    private fun navigateToAddEditTransaction() {
        val motionDuration = resources.getInteger(R.integer.shared_element_motion_duration).toLong()
        val extras = FragmentNavigatorExtras(
            binding.editTransaction to resources.getString(R.string.transition_name_add_edit_transaction)
        )

        exitTransition = Hold().apply {
            duration = motionDuration
        }
        reenterTransition = null

        findNavController().navigate(
            TransactionDetailsFragmentDirections.actionTransactionDetailsFragmentToAddEditFragment(
                transactionId = args.transactionId
            ),
            extras
        )
    }
}