package ai.kaira.app.home.viewmodel

import ai.kaira.app.application.BaseViewModel
import ai.kaira.domain.ErrorAction
import ai.kaira.domain.KairaAction
import ai.kaira.domain.KairaResult
import ai.kaira.domain.ResultState
import ai.kaira.domain.financial.model.MyFinancials
import ai.kaira.domain.financial.usecase.MyFinancialUseCase
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyFinanceViewModel (private val myFinancialUseCase: MyFinancialUseCase) : BaseViewModel() {

    private val myFinancialsLiveData = MutableLiveData<MyFinancials>()

    private lateinit var flow : Flow<KairaResult<MyFinancials>>

    fun fetchMyFinancials(){
        flow = myFinancialUseCase.GetMyFinancials()
        viewModelScope.launch(IO){
            flow.collect { result ->
                withContext(Main){
                    when(result.status){
                        ResultState.SUCCESS ->{
                            result.data?.let{
                                myFinancialsLiveData.value = it
                            }

                            showLoading(false)
                        }
                        ResultState.ERROR ->{
                            showLoading(false)
                            if(result.kairaAction != null){
                                if(result.kairaAction == KairaAction.UNAUTHORIZED_REDIRECT){
                                    myFinancialUseCase.deleteToken()
                                }
                                errorAction(ErrorAction(result.message.toString(),result.kairaAction))
                            }else{
                                result.message?.let{
                                    showError(it)
                                }
                            }
                        }
                        ResultState.EXCEPTION->{
                            showLoading(false)
                            showConnectivityError()
                        }
                        ResultState.LOADING->{
                            showLoading(true)
                        }
                    }
                }
            }
        }
    }

    fun onMyFinancialsFetched() : MutableLiveData<MyFinancials>{
        return myFinancialsLiveData
    }
}