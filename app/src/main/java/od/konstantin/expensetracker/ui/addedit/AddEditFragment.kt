package od.konstantin.expensetracker.ui.addedit

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import od.konstantin.expensetracker.R
import od.konstantin.expensetracker.databinding.FragmentAddEditBinding
import od.konstantin.expensetracker.di.components.DaggerAddEditComponent
import od.konstantin.expensetracker.domain.models.TransactionTag
import od.konstantin.expensetracker.domain.models.TransactionType
import od.konstantin.expensetracker.presenters.addedit.AddEditPresenter
import od.konstantin.expensetracker.presenters.addedit.AddEditView
import od.konstantin.expensetracker.presenters.addedit.TransactionForm
import od.konstantin.expensetracker.presenters.addedit.TransactionFormError
import od.konstantin.expensetracker.utils.extensions.appComponent
import od.konstantin.expensetracker.utils.extensions.format
import od.konstantin.expensetracker.utils.extensions.hideKeyboard
import od.konstantin.expensetracker.utils.extensions.themeColor
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class AddEditFragment : MvpAppCompatFragment(R.layout.fragment_add_edit), AddEditView {

    @Inject
    lateinit var presenterProvider: Provider<AddEditPresenter>

    private val presenter: AddEditPresenter by moxyPresenter { presenterProvider.get() }

    private val binding: FragmentAddEditBinding by viewBinding(FragmentAddEditBinding::bind)

    private val args: AddEditFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerAddEditComponent.factory().create(appComponent)
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTransitions()
        initTransactionDateInput()
        initListeners()
        initAdapters()

        if (savedInstanceState == null && args.transactionId != NONE_TRANSACTION_ID) {
            presenter.loadTransactionToEdit(args.transactionId)
        }
    }

    override fun showTransactionFormError(formError: TransactionFormError): Unit = with(binding) {
        val requiredFieldError = getString(R.string.required_field_error)
        transactionTitleTextInput.error = if (formError.isTitleEmpty) {
            requiredFieldError
        } else ""
        transactionTitleTextInput.error = if (formError.isTitleEmpty) {
            requiredFieldError
        } else ""
        transactionAmountTextInput.error = if (formError.isAmountEmpty) {
            requiredFieldError
        } else ""
        transactionTypeTextInput.error = if (formError.isTypeEmpty) {
            requiredFieldError
        } else ""
        transactionTagTextInput.error = if (formError.isTagEmpty) {
            requiredFieldError
        } else ""
        transactionDateTextInput.error = if (formError.isDateEmpty) {
            requiredFieldError
        } else ""
    }

    override fun showTransactionForm(form: TransactionForm): Unit = with(binding) {
        transactionTitleInput.setText(form.title)
        transactionAmountInput.setText(form.amount.toString())
        transactionDateInput.setText(form.transactionDate?.format(requireContext()))
        form.transactionType?.let {
            transactionTypeDropdown.setText(
                resources.getStringArray(R.array.transaction_types)[it.ordinal]
            )
        }
        form.transactionTag?.let {
            transactionTagDropdown.setText(
                resources.getStringArray(R.array.transaction_tags)[it.ordinal]
            )
        }
        initAdapters()
    }

    override fun navigateToPreviousFragment() {
        hideKeyboard()
        findNavController().popBackStack()
    }

    private fun initListeners(): Unit = with(binding) {
        transactionTitleInput.addTextChangedListener { title ->
            presenter.setTransactionTitle(title.toString())
        }
        transactionAmountInput.addTextChangedListener { amount ->
            presenter.setTransactionAmount(amount.toString())
        }
        transactionTypeDropdown.setOnItemClickListener { _, _, position, _ ->
            presenter.setTransactionType(TransactionType.values()[position])
        }
        transactionTagDropdown.setOnItemClickListener { _, _, position, _ ->
            presenter.setTransactionTag(TransactionTag.values()[position])
        }
        saveTransaction.setOnClickListener {
            presenter.saveButtonPressed()
        }
    }

    private fun initTransactionDateInput() {
        val dateInput = binding.transactionDateInput
        val datePicker = createDatePicker()
        datePicker.addOnPositiveButtonClickListener { longDate ->
            val selectedDate = Date(longDate)
            dateInput.setText(selectedDate.format(requireContext()))
            presenter.setTransactionDate(selectedDate)
        }
        dateInput.setOnClickListener {
            datePicker.show(childFragmentManager, TRANSACTION_DATE_PICKER_TAG)
        }
    }

    private fun initAdapters() {
        initTransactionTypeAdapter()
        initTransactionTagAdapter()
    }

    private fun initTransactionTypeAdapter() {
        binding.transactionTypeDropdown.setAdapter(
            createDropdownAdapter(R.array.transaction_types)
        )
    }

    private fun initTransactionTagAdapter() {
        binding.transactionTagDropdown.setAdapter(
            createDropdownAdapter(R.array.transaction_tags)
        )
    }

    private fun createDropdownAdapter(arrayId: Int): ArrayAdapter<CharSequence> {
        return ArrayAdapter.createFromResource(
            requireContext(),
            arrayId,
            R.layout.item_dropdown_popup
        )
    }

    private fun createDatePicker(): MaterialDatePicker<Long> {
        return MaterialDatePicker.Builder.datePicker()
            .setTheme(R.style.transaction_date_picker_style)
            .build()
    }

    private fun initTransitions() {
        val motionDuration = resources.getInteger(R.integer.shared_element_motion_duration).toLong()
        val secondaryColor = requireContext().themeColor(R.attr.colorSecondary)
        val windowBackgroundColor = requireContext().themeColor(android.R.attr.windowBackground)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = motionDuration
            scrimColor = Color.TRANSPARENT
            startContainerColor = secondaryColor
            endContainerColor = windowBackgroundColor
            pathMotion = MaterialArcMotion()
        }

        sharedElementReturnTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = motionDuration
            scrimColor = Color.TRANSPARENT
            startContainerColor = windowBackgroundColor
            endContainerColor = secondaryColor
            pathMotion = MaterialArcMotion()
        }
    }

    companion object {
        private const val TRANSACTION_DATE_PICKER_TAG = "transaction_date_picker"
        private const val NONE_TRANSACTION_ID = -1
    }
}