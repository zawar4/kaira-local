package ai.kaira.app.utils

import ai.kaira.app.utils.Extensions.Companion.increaseViewSize
import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import dagger.hilt.android.qualifiers.ApplicationContext
import java.net.HttpURLConnection
import java.net.InetSocketAddress
import java.net.Socket
import java.net.URL

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


    }

}