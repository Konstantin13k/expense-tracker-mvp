package od.konstantin.expensetracker.di.components

import dagger.Component
import od.konstantin.expensetracker.di.scopes.FragmentScope
import od.konstantin.expensetracker.ui.dashboard.DashboardFragment

@FragmentScope
@Component(dependencies = [AppComponent::class])
interface DashboardComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): DashboardComponent
    }

    fun inject(fragment: DashboardFragment)
}