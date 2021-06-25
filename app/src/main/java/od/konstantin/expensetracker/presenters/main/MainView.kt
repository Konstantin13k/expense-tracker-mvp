package od.konstantin.expensetracker.presenters.main

import moxy.MvpView
import moxy.viewstate.strategy.alias.OneExecution
import od.konstantin.expensetracker.domain.models.ThemeMode

interface MainView : MvpView {

    @OneExecution
    fun setThemeMode(themeMode: ThemeMode)
}