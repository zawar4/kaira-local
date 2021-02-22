package ai.kaira.app.utils

import android.content.Context
import java.net.InetSocketAddress
import java.net.Socket

class Extensions {


    companion object{
        fun Context.readFileText(fileName: String): String {
            return assets.open(fileName).bufferedReader().use { it.readText() }
        }

        fun isConnectedToInternet(): Boolean {
            return try {
                val socket = Socket()
                val socketAddress = InetSocketAddress("8.8.8.8", 53)
                socket.connect(socketAddress, 2000)
                socket.close()
                true
            } catch (e: Exception) {
                false
            }
        }
    }

}