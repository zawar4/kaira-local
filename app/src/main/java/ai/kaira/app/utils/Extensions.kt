package ai.kaira.app.utils

import android.content.Context

class Extensions {


    companion object{
        fun Context.readFileText(fileName: String): String {
            return assets.open(fileName).bufferedReader().use { it.readText() }
        }
    }

}