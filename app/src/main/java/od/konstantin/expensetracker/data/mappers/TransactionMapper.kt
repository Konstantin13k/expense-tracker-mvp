package od.konstantin.expensetracker.data.mappers

import od.konstantin.expensetracker.data.local.transactions.entities.TransactionEntity
import od.konstantin.expensetracker.domain.models.Transaction
import od.konstantin.expensetracker.domain.models.TransactionTag
import od.konstantin.expensetracker.domain.models.TransactionType
import java.util.*
import javax.inject.Inject

class TransactionMapper @Inject constructor() {

    fun toEntity(transaction: Transaction): TransactionEntity {
        with(transaction) {
            return TransactionEntity(
                transactionId = transactionId,
                title = title,
                amount = amount,
                typeId = type.ordinal,
                tagId = tag.ordinal,
                date = date.time,
                createdAt = createdAt.time
            )
        }
    }

    fun fromEntity(transactionEntity: TransactionEntity): Transaction {
        with(transactionEntity) {
            return Transaction(
                transactionId = transactionId,
                title = title,
                amount = amount,
                type = TransactionType.values()[typeId],
                tag = TransactionTag.values()[tagId],
                date = Date(date),
                createdAt = Date(createdAt)
            )
        }
    }
}