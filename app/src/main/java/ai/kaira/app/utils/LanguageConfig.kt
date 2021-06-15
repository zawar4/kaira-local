package ai.kaira.app.utils

import android.content.Context
import androidx.core.os.ConfigurationCompat
import java.util.*

class LanguageConfig {
    companion object{

        const val CANADIAN_FRENCH :String = "fr-CA"
        const val CANADIAN_ENGLISH :String = "en-CA"
        const val FRENCH :String = "fr-FR"

        fun getLanguageLocale(context: Context): String {
            return ConfigurationCompat.getLocales(context.resources.configuration)[0].toLanguageTag()
        }

        fun getLanguage(context: Context) : Locale {
            return Locale(ConfigurationCompat.getLocales(context.resources.configuration)[0].language)
        }
    }
}