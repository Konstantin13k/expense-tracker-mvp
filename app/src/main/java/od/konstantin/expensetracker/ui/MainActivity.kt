package od.konstantin.expensetracker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.appbar.MaterialToolbar
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import od.konstantin.expensetracker.ExpenseTrackerApp
import od.konstantin.expensetracker.R
import od.konstantin.expensetracker.databinding.ActivityMainBinding
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

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerMainActivityComponent.factory().create(
            (application as ExpenseTrackerApp).appComponent
        ).inject(this)
        super.onCreate(savedInstanceState)
        setupToolbarMenu()
        presenter.loadCurrentThemeMode()
        setupActionBarWithNavController()
    }

    private fun setupActionBarWithNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

    }

    private fun setupToolbarMenu() {
        val toolbar: MaterialToolbar = binding.toolbar
        val themeItem = toolbar.menu?.findItem(R.id.switch_theme)
        themeItem?.setOnMenuItemClickListener {
            presenter.switchThemeMode()
            true
        }
    }

    override fun setThemeMode(themeMode: ThemeMode) {
        val themeItem = binding.toolbar.menu?.findItem(R.id.switch_theme)
        if (themeMode.isLight) {
            themeItem?.setIcon(R.drawable.ic_dark_mode)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            themeItem?.setIcon(R.drawable.ic_light_mode)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}