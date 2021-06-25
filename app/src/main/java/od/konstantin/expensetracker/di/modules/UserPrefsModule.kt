package od.konstantin.expensetracker.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import od.konstantin.expensetracker.data.userprefs.UserPrefsImpl
import od.konstantin.expensetracker.domain.userprefs.UserPrefs
import javax.inject.Singleton

@Module
class UserPrefsModule {

    @Singleton
    @Provides
    fun provideUserPrefs(context: Context): UserPrefs {
        return UserPrefsImpl(context)
    }
}