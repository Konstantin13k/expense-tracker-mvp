package od.konstantin.expensetracker.presenters.main

import moxy.MvpPresenter
import od.konstantin.expensetracker.domain.models.ThemeMode
import od.konstantin.expensetracker.domain.userprefs.UserPrefs
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val userPrefs: UserPrefs
) : MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setThemeMode(userPrefs.themeMode)
    }

    fun loadCurrentThemeMode() {
        viewState.setThemeMode(userPrefs.themeMode)
    }

    fun switchThemeMode() {
        val currentThemeMode = userPrefs.themeMode
        val newThemeMode = if (currentThemeMode.isLight) {
            ThemeMode.DARK
        } else {
            ThemeMode.LIGHT
        }

        userPrefs.themeMode = newThemeMode
        viewState.setThemeMode(newThemeMode)
    }
}