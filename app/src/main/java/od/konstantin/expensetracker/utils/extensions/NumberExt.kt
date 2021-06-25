package od.konstantin.expensetracker.utils.extensions

import java.text.NumberFormat
import java.util.*

fun Double.format(prefix: String = ""): String {
    return "$prefix${NumberFormat.getInstance(Locale.getDefault()).format(this)}"
}