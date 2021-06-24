package od.konstantin.expensetracker.di.components

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import od.konstantin.expensetracker.di.modules.TransactionsDatabaseModule
import od.konstantin.expensetracker.di.modules.TransactionsModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        TransactionsDatabaseModule::class,
        TransactionsModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}