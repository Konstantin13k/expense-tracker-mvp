package od.konstantin.expensetracker.data.mappers

import od.konstantin.expensetracker.data.local.transactions.entities.TransactionsInfo
import od.konstantin.expensetracker.domain.models.BalanceInfo
import od.konstantin.expensetracker.domain.models.TransactionType
import javax.inject.Inject

class BalanceInfoMapper @Inject constructor() {

    fun fromTransactionsInfo(transactionsInfoList: List<TransactionsInfo>): BalanceInfo {
        val totalIncome = transactionsInfoList
            .filter { it.transactionTypeId == TransactionType.INCOME.ordinal }
            .sumOf { it.total }
        val totalExpense = transactionsInfoList
            .filter { it.transactionTypeId == TransactionType.EXPENSE.ordinal }
            .sumOf { it.total }

        return BalanceInfo(
            balance = totalIncome - totalExpense,
            totalIncome = totalIncome,
            totalExpense = totalExpense
        )
    }
}