package od.konstantin.expensetracker.di.components

import dagger.Component
import od.konstantin.expensetracker.di.scopes.ActivityScope
import od.konstantin.expensetracker.ui.MainActivity

@ActivityScope
@Component(dependencies = [AppComponent::class])
interface MainActivityComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): MainActivityComponent
    }

    fun inject(activity: MainActivity)
}