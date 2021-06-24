package od.konstantin.expensetracker.di.components

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import od.konstantin.expensetracker.di.modules.TransactionsDatabaseModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        TransactionsDatabaseModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}