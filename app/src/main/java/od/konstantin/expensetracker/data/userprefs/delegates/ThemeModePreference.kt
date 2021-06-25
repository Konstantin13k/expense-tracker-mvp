package od.konstantin.expensetracker.data.userprefs.delegates

import android.content.SharedPreferences
import od.konstantin.expensetracker.domain.models.ThemeMode
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private const val THEME_MODE_KEY = "theme_mode"

class ThemeModePreference(
    private val defValue: ThemeMode = ThemeMode.LIGHT
) : ReadWriteProperty<SharedPreferences, ThemeMode> {

    override fun getValue(thisRef: SharedPreferences, property: KProperty<*>): ThemeMode {
        val themeModeId = thisRef.getInt(THEME_MODE_KEY, defValue.ordinal)
        return ThemeMode.values()[themeModeId]
    }

    override fun setValue(thisRef: SharedPreferences, property: KProperty<*>, value: ThemeMode) {
        thisRef.edit().putInt(THEME_MODE_KEY, value.ordinal).apply()
    }
}