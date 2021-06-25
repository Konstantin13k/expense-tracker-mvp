package od.konstantin.expensetracker.domain.models

enum class ThemeMode {
    LIGHT, DARK;

    val isLight: Boolean
        get() = this == LIGHT

    val isDark: Boolean
        get() = this == DARK
}