package od.konstantin.expensetracker.utils.extensions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import od.konstantin.expensetracker.ExpenseTrackerApp
import od.konstantin.expensetracker.di.components.AppComponent

val Fragment.appComponent: AppComponent
    get() = (requireActivity().application as ExpenseTrackerApp).appComponent

fun Fragment.hideKeyboard(): Boolean {
    return (context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow((activity?.currentFocus ?: View(context)).windowToken, 0)
}