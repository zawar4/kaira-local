package ai.kaira.app.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import java.net.InetSocketAddress
import java.net.Socket
import javax.inject.Inject

open class BaseViewModel @Inject constructor() : ViewModel() {


    private val loadLiveData : MutableLiveData<Boolean> = MutableLiveData()
    private val errorLiveData : MutableLiveData<String> = MutableLiveData()
    private val connectivityError : MutableLiveData<Boolean> = MutableLiveData()
    private val viewModelCoroutineScope : CoroutineScope = viewModelScope

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

    fun onConnectivityError() : LiveData<Boolean>{
        return connectivityError
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

    fun showConnectivityError(){
        connectivityError.value = true
    }


    override fun onCleared(){
        viewModelCoroutineScope.cancel()
        super.onCleared()
    }
}