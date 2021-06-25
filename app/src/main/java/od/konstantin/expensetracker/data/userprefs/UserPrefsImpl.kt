package od.konstantin.expensetracker.data.userprefs

import android.content.Context
import android.content.SharedPreferences
import od.konstantin.expensetracker.data.userprefs.delegates.ThemeModePreference
import od.konstantin.expensetracker.domain.models.ThemeMode
import od.konstantin.expensetracker.domain.userprefs.UserPrefs

private const val SHARED_PREFS_NAME = "user_prefs"

internal class UserPrefsImpl(
    context: Context
) : UserPrefs,
    SharedPreferences by context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE) {

    override var themeMode: ThemeMode by ThemeModePreference()
}