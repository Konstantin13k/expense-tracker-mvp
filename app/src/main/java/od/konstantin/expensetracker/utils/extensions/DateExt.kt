package od.konstantin.expensetracker.utils.extensions

import android.content.Context
import od.konstantin.expensetracker.R
import java.text.SimpleDateFormat
import java.util.*

fun Date.format(context: Context): String {
    val simpleDate = SimpleDateFormat(
        context.getString(R.string.transaction_date_format), Locale.getDefault()
    )
    return simpleDate.format(this)
}