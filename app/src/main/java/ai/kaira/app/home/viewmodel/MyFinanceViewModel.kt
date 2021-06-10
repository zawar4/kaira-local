package ai.kaira.app.home.viewmodel

import ai.kaira.app.application.BaseViewModel
import ai.kaira.domain.ErrorAction
import ai.kaira.domain.KairaAction
import ai.kaira.domain.KairaResult
import ai.kaira.domain.ResultState
import ai.kaira.domain.financial.model.MyFinancials
import ai.kaira.domain.financial.usecase.MyFinancialUseCase
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

class MyFinanceViewModel (private val myFinancialUseCase: MyFinancialUseCase) : BaseViewModel() {

    private val myFinancialsLiveData = MediatorLiveData<MyFinancials>()

    fun fetchMyFinancials(){
        val liveDataSource = myFinancialUseCase.GetMyFinancials()
        myFinancialsLiveData.addSource(liveDataSource){ result ->
            when(result.status){
                ResultState.SUCCESS ->{
                    showLoading(false)
                    myFinancialsLiveData.removeSource(liveDataSource)
                }
                ResultState.ERROR ->{
                    showLoading(false)
                    myFinancialsLiveData.removeSource(liveDataSource)
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
                    myFinancialsLiveData.removeSource(liveDataSource)
                }
                ResultState.LOADING->{
                    showLoading(true)
                }
            }
        }
    }

    fun onMyFinancialsFetched() : MediatorLiveData<MyFinancials>{
        return myFinancialsLiveData
    }
}