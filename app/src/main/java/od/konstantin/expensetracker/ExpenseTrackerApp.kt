package od.konstantin.expensetracker

import android.app.Application
import od.konstantin.expensetracker.di.components.AppComponent
import od.konstantin.expensetracker.di.components.DaggerAppComponent

class ExpenseTrackerApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}