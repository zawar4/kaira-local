package ai.kaira.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.net.InetSocketAddress
import java.net.Socket

open class BaseViewModel : ViewModel() {


    val onLoadLiveData : MutableLiveData<Boolean> = MutableLiveData()
    val onErrorLiveData : MutableLiveData<String> = MutableLiveData()

    public fun isConnectedToInternet(): Boolean {
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

    fun showLoading(show:Boolean){
        onLoadLiveData.value = show
    }

    fun showError(error:String){
        onErrorLiveData.value = error
    }
}