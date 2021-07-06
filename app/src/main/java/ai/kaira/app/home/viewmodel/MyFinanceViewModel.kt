package ai.kaira.app.home.viewmodel

import ai.kaira.app.application.BaseViewModel
import ai.kaira.app.utils.Extensions.Companion.getFormattedZoneDate
import ai.kaira.domain.ErrorAction
import ai.kaira.domain.KairaAction
import ai.kaira.domain.KairaResult
import ai.kaira.domain.ResultState
import ai.kaira.domain.financial.model.MyFinancials
import ai.kaira.domain.financial.model.Transaction
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
    private val myTransactionListLiveData  = MutableLiveData<ArrayList<Transaction>>()
    private var transactions = ArrayList<Transaction>()
    private lateinit var flow : Flow<KairaResult<MyFinancials>>



    fun getTransactionsGroupByDate() : HashMap<String,ArrayList<Transaction>> {
        val groupedTransactions = HashMap<String,ArrayList<Transaction>>()
        for(transaction in transactions) {
            if(groupedTransactions.containsKey(transaction.date.getFormattedZoneDate())) {
                val list = groupedTransactions.get(transaction.date.getFormattedZoneDate())
                list?.add(transaction)
            } else {
                val list = ArrayList<Transaction>()
                list.add(transaction)
                groupedTransactions.put(transaction.date.getFormattedZoneDate(),list)
            }
        }
        return groupedTransactions
    }
    fun onMyTransactionListFetched() : MutableLiveData<ArrayList<Transaction>> {
        return myTransactionListLiveData
    }
    fun fetchMyTransactionList(accountId : String) {
        viewModelCoroutineScope.launch(IO) {
            myFinancialUseCase.getMyTransactionList(accountId).collect{ result ->
                withContext(Main) {
                    when(result.status) {
                        ResultState.SUCCESS -> {
                            result.data?.let {
                                transactions = it
                                getTransactionsGroupByDate()
                                myTransactionListLiveData.value = it
                            }
                        }
                        ResultState.ERROR -> {
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
                        ResultState.LOADING -> {
                            showLoading(true)
                        }
                        ResultState.EXCEPTION -> {

                        }
                    }
                }
            }
        }
    }

    fun fetchMyFinancials(){
        flow = myFinancialUseCase.GetMyFinancials()
        viewModelCoroutineScope.launch(IO){
            flow.collect { result ->
                withContext(Main){
                    when(result.status){
                        ResultState.SUCCESS -> {
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