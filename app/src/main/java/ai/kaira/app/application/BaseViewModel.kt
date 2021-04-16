package ai.kaira.app.application

import ai.kaira.domain.ErrorAction
import ai.kaira.domain.KairaAction
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
    private val errorActionLiveData : MutableLiveData<ErrorAction> = MutableLiveData()
    private val finishActivityLiveData : MutableLiveData<Unit> = MutableLiveData()
    private val connectivityError : MutableLiveData<Boolean> = MutableLiveData()
    private val viewModelCoroutineScope : CoroutineScope = viewModelScope


    fun onLoad() : LiveData<Boolean>{
        return loadLiveData
    }

    fun onConnectivityError() : LiveData<Boolean>{
        return connectivityError
    }
    fun onError() : LiveData<String>{
        return errorLiveData
    }

    fun onActivityFinish():LiveData<Unit>{
        return finishActivityLiveData
    }
    fun showLoading(show:Boolean){
        loadLiveData.value = show
    }

    fun showError(error:String){
        errorLiveData.value = error
    }

    fun errorAction(errorAction: ErrorAction){
        errorActionLiveData.value = errorAction
    }

    fun onErrorAction():LiveData<ErrorAction>{
        return errorActionLiveData
    }
    fun finishActivity(){
        finishActivityLiveData.value = Unit
    }

    fun showConnectivityError(){
        connectivityError.value = true
    }


    override fun onCleared(){
        viewModelCoroutineScope.cancel()
        super.onCleared()
    }
}