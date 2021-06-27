package od.konstantin.expensetracker.common

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class SwipeToDeleteCallback(
    private val recyclerView: RecyclerView,
    private val deletedOneTransactionText: String,
    private val undoDeletedTransactionText: String,
    private val onSwipe: (Int) -> Unit,
    private val onUndo: () -> Unit
) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.absoluteAdapterPosition
        onSwipe(position)

        Snackbar.make(recyclerView, deletedOneTransactionText, Snackbar.LENGTH_LONG)
            .setAction(undoDeletedTransactionText) {
                onUndo()
            }.show()
    }
}