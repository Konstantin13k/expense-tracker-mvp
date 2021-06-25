package od.konstantin.expensetracker.di.components

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import od.konstantin.expensetracker.di.modules.CoroutineModule
import od.konstantin.expensetracker.di.modules.TransactionsDatabaseModule
import od.konstantin.expensetracker.di.modules.TransactionsModule
import od.konstantin.expensetracker.domain.repositories.TransactionsRepository
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CoroutineModule::class,
        TransactionsDatabaseModule::class,
        TransactionsModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun transactionsRepository(): TransactionsRepository
}