package od.konstantin.expensetracker.di.components

import dagger.Component
import od.konstantin.expensetracker.di.scopes.FragmentScope
import od.konstantin.expensetracker.ui.details.TransactionDetailsFragment

@FragmentScope
@Component(dependencies = [AppComponent::class])
interface DetailsComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): DetailsComponent
    }

    fun inject(fragment: TransactionDetailsFragment)
}