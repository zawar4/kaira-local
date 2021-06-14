package ai.kaira.app.utils

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.text.HtmlCompat
import java.net.HttpURLConnection
import java.net.URL
import java.text.Normalizer

class Extensions {


    companion object{
        fun Context.readFileText(fileName: String): String {
            return assets.open(fileName).bufferedReader().use { it.readText() }
        }

        fun isConnectedToInternet(): Boolean {
            return try {
                val connection: HttpURLConnection = URL("https://clients3.google.com/generate_204")
                        .openConnection() as HttpURLConnection
                connection.responseCode == 204 && connection.contentLength == 0
            } catch (e: Exception) {
                false
            }
        }

        fun Context.clearCache(){
            getSharedPreferences("kaira", Context.MODE_PRIVATE).edit().clear().apply()
        }

        fun Context.clearToken(){
            getSharedPreferences("kaira", Context.MODE_PRIVATE).edit().remove("token").apply()
        }

        fun Context.isLoggedIn():Boolean{
            return getSharedPreferences("kaira", Context.MODE_PRIVATE).contains("token")
        }

        fun Context.ignoreInstitutionAddition(){
            getSharedPreferences("kaira", Context.MODE_PRIVATE).edit().putBoolean("ignore_institution_addition",true).apply()
        }

        fun Context.institutionAdditionIgnored():Boolean{
            return getSharedPreferences("kaira", Context.MODE_PRIVATE).contains("ignore_institution_addition")
        }

        fun View.increaseViewSize(duration: Long, maxHeight: Int, minHeight: Int) {
            val valueAnimator = ValueAnimator.ofInt(minHeight, maxHeight)
            valueAnimator.duration = duration
            valueAnimator.addUpdateListener {
                val animatedValue = valueAnimator.animatedValue as Int
                val layoutParams = this.layoutParams
                layoutParams.height = animatedValue
                layoutParams.width = animatedValue
                this.layoutParams = layoutParams
            }
            valueAnimator.start()
        }

        fun View.decreaseViewSize(duration: Long, maxHeight: Int, minHeight: Int) {
            val valueAnimator = ValueAnimator.ofInt(maxHeight, minHeight)
            valueAnimator.duration = duration
            valueAnimator.addUpdateListener {
                val animatedValue = valueAnimator.animatedValue as Int
                val layoutParams = this.layoutParams
                layoutParams.height = animatedValue
                layoutParams.width = animatedValue
                this.layoutParams = layoutParams
            }
            valueAnimator.start()
        }

        fun TextView.setHtmlText(source: String) {
            this.text = HtmlCompat.fromHtml(source, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }

        fun Activity.dismissKeyboard() {
            val v: View? = window.currentFocus
            if (v != null) {
                (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(v.windowToken, 0)
            }
        }

        private val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()

        fun CharSequence.unaccent(): String {
            val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
            return REGEX_UNACCENT.replace(temp, "")
        }


        fun Double.getFormattedAmount():String{
            return String.format("%.2f", this)
        }


    }

}