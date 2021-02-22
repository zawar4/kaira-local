package ai.kaira.app.utils

import android.content.Context
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
    }

}