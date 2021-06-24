package od.konstantin.expensetracker.ui.addedit

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import od.konstantin.expensetracker.R
import od.konstantin.expensetracker.databinding.FragmentAddEditBinding

class AddEditFragment : Fragment(R.layout.fragment_add_edit) {

    private val binding: FragmentAddEditBinding by viewBinding(FragmentAddEditBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}