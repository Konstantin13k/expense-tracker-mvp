package od.konstantin.expensetracker.ui.addedit

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.datepicker.MaterialDatePicker
import od.konstantin.expensetracker.R
import od.konstantin.expensetracker.databinding.FragmentAddEditBinding
import od.konstantin.expensetracker.utils.extensions.format
import java.util.*

class AddEditFragment : Fragment(R.layout.fragment_add_edit) {

    private val binding: FragmentAddEditBinding by viewBinding(FragmentAddEditBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTransactionDateInput()
        initAdapters()
    }

    private fun initTransactionDateInput() {
        val dateInput = binding.transactionDateInput
        val datePicker = createDatePicker()
        datePicker.addOnPositiveButtonClickListener { longDate ->
            val selectedDate = Date(longDate)
            dateInput.setText(selectedDate.format(requireContext()))
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

    companion object {
        private const val TRANSACTION_DATE_PICKER_TAG = "transaction_date_picker"
    }
}