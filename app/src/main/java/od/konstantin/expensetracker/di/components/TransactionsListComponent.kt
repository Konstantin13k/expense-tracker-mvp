package od.konstantin.expensetracker.di.components

import dagger.Component
import od.konstantin.expensetracker.di.scopes.FragmentScope
import od.konstantin.expensetracker.ui.transactionslist.TransactionsListFragment

@FragmentScope
@Component(dependencies = [AppComponent::class])
interface TransactionsListComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): TransactionsListComponent
    }

    fun inject(fragment: TransactionsListFragment)
}