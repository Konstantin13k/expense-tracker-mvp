package od.konstantin.expensetracker.utils.extensions

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import od.konstantin.expensetracker.R
import od.konstantin.expensetracker.domain.models.TransactionTag
import od.konstantin.expensetracker.domain.models.TransactionType

fun TransactionType.getColor(resources: Resources): Int {
    val colorId = when (this) {
        TransactionType.INCOME -> R.color.income_color
        TransactionType.EXPENSE -> R.color.expense_color
    }
    return ResourcesCompat.getColor(resources, colorId, null)
}

fun TransactionTag.getIcon(resources: Resources): Drawable? {
    val drawableId = when (this) {
        TransactionTag.WORK -> R.drawable.ic_work
        TransactionTag.TRANSPORT -> R.drawable.ic_transport
        TransactionTag.PERSON -> R.drawable.ic_person
        TransactionTag.FOOD -> R.drawable.ic_food
        TransactionTag.OTHER -> R.drawable.ic_other
    }
    return ResourcesCompat.getDrawable(resources, drawableId, null)
}