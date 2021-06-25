package od.konstantin.expensetracker.utils.extensions

import androidx.fragment.app.Fragment
import od.konstantin.expensetracker.ExpenseTrackerApp
import od.konstantin.expensetracker.di.components.AppComponent

val Fragment.appComponent: AppComponent
    get() = (requireActivity().application as ExpenseTrackerApp).appComponent