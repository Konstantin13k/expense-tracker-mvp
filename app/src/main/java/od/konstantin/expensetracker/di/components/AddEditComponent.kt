package od.konstantin.expensetracker.di.components

import dagger.Component
import od.konstantin.expensetracker.di.scopes.FragmentScope
import od.konstantin.expensetracker.ui.addedit.AddEditFragment

@FragmentScope
@Component(dependencies = [AppComponent::class])
interface AddEditComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): AddEditComponent
    }

    fun inject(fragment: AddEditFragment)
}