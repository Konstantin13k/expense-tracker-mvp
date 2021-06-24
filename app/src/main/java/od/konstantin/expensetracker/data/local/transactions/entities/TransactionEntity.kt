package od.konstantin.expensetracker.data.local.transactions.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
class TransactionEntity(
    @ColumnInfo(name = "transaction_id")
    @PrimaryKey(autoGenerate = true)
    val transactionId: Int? = null,
    @ColumnInfo(name = "transaction_title")
    val title: String,
    @ColumnInfo(name = "transaction_amount")
    val amount: Double,
    @ColumnInfo(name = "transaction_type_id")
    val typeId: Int,
    @ColumnInfo(name = "transaction_tag_id")
    val tagId: Int,
    @ColumnInfo(name = "transaction_date")
    val date: Long,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
)