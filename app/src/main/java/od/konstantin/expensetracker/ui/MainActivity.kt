package od.konstantin.expensetracker.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import od.konstantin.expensetracker.ExpenseTrackerApp
import od.konstantin.expensetracker.R
import od.konstantin.expensetracker.di.components.DaggerMainActivityComponent
import od.konstantin.expensetracker.domain.models.ThemeMode
import od.konstantin.expensetracker.presenters.main.MainPresenter
import od.konstantin.expensetracker.presenters.main.MainView
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : MvpAppCompatActivity(R.layout.activity_main), MainView {

    @Inject
    lateinit var provideMainPresenter: Provider<MainPresenter>

    private val presenter: MainPresenter by moxyPresenter { provideMainPresenter.get() }

    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerMainActivityComponent.factory().create(
            (application as ExpenseTrackerApp).appComponent
        ).inject(this)
        super.onCreate(savedInstanceState)

        presenter.loadCurrentThemeMode()
        setupActionBarWithNavController()
    }

    private fun setupActionBarWithNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.switch_theme, menu)
        this.menu = menu

        menu?.findItem(R.id.switch_theme)?.setThemeIcon(
            isLight = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO
        )
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.switch_theme -> {
                presenter.switchThemeMode()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setThemeMode(themeMode: ThemeMode) {
        val themeItem = menu?.findItem(R.id.switch_theme)
        themeItem?.setThemeIcon(themeMode.isLight)
        if (themeMode.isLight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    override fun onDestroy() {
        menu = null
        super.onDestroy()
    }

    private fun MenuItem.setThemeIcon(isLight: Boolean) {
        if (isLight) {
            setIcon(R.drawable.ic_dark_mode)
        } else {
            setIcon(R.drawable.ic_light_mode)
        }
    }
}