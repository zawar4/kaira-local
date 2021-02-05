package ai.kaira.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.net.InetSocketAddress
import java.net.Socket

open class BaseViewModel : ViewModel() {


    protected val loadLiveData : MutableLiveData<Boolean> = MutableLiveData()
    protected val errorLiveData : MutableLiveData<String> = MutableLiveData()


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

    fun onLoad() : LiveData<Boolean>{
        return loadLiveData
    }

    fun onError() : LiveData<String>{
        return errorLiveData
    }
    fun showLoading(show:Boolean){
        loadLiveData.value = show
    }

    fun showError(error:String){
        errorLiveData.value = error
    }
}