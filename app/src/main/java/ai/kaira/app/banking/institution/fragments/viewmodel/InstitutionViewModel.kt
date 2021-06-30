package ai.kaira.app.banking.institution.fragments.viewmodel

import ai.kaira.app.application.BaseViewModel
import ai.kaira.domain.ErrorAction
import ai.kaira.domain.KairaAction
import ai.kaira.domain.KairaResult
import ai.kaira.domain.ResultState
import ai.kaira.domain.banking.institution.model.ConnectedInstitution
import ai.kaira.domain.banking.institution.model.Institution
import ai.kaira.domain.banking.institution.model.InstitutionParamBody
import ai.kaira.domain.banking.institution.model.SecurityAnswer
import ai.kaira.domain.banking.institution.usecase.ConnectInstitution
import ai.kaira.domain.banking.institution.usecase.InstitutionUseCase
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InstitutionViewModel(private val institutionUseCase: InstitutionUseCase) : BaseViewModel() {

    private val connectInstitutionLiveData = MediatorLiveData<Boolean>()
    private val institutionAccountVerificationCodeLiveData = MutableLiveData<Boolean>()
    private val myInstitutionsLiveData = MutableLiveData<ArrayList<Institution>>()
    private val institutionRemoveLiveData = MutableLiveData<Boolean>()


    fun onInstitutionRemoved() : MutableLiveData<Boolean> {
        return institutionRemoveLiveData
    }
    fun removeInstitution(aggregator: Int, institutionId: String) {
        viewModelCoroutineScope.launch(IO) {
            institutionUseCase.removeInstitution(aggregator,institutionId).collect {
                withContext(Main) {
                    when(it.status) {
                        ResultState.SUCCESS -> {
                            showLoading(false)
                            institutionRemoveLiveData.value = true
                        }
                        ResultState.LOADING -> {
                            showLoading(true)
                        }
                        ResultState.ERROR -> {
                            showLoading(false)
                            if(it.kairaAction != null){
                                if(it.kairaAction == KairaAction.UNAUTHORIZED_REDIRECT){
                                    institutionUseCase.deleteToken()
                                }
                                it.kairaAction?.let{ it2 ->
                                    errorAction(ErrorAction(it.message.toString(),it2))
                                }
                            }else {
                                it.message?.let{ error ->
                                    showError(error)
                                }
                            }
                        }
                        ResultState.EXCEPTION -> {
                            showLoading(false)
                            it.message?.let { it2 ->
                                showConnectivityError()
                            }
                        }
                    }
                }
            }
        }
    }

    fun onMyInstitutionsFetched() : MutableLiveData<ArrayList<Institution>> {
        return myInstitutionsLiveData
    }

    fun getMyInstitutions() {
        viewModelCoroutineScope.launch(IO) {
            institutionUseCase.getMyInstitutions().collect {
                withContext(Main) {
                    when(it.status){
                        ResultState.SUCCESS -> {
                            showLoading(false)
                            it.data?.let { institutions ->
                                myInstitutionsLiveData.value = institutions
                            }

                        }
                        ResultState.ERROR -> {
                            showLoading(false)
                            if(it.kairaAction != null){
                                if(it.kairaAction == KairaAction.UNAUTHORIZED_REDIRECT){
                                    institutionUseCase.deleteToken()
                                }
                                it.kairaAction?.let{ it2 ->
                                    errorAction(ErrorAction(it.message.toString(),it2))
                                }
                            }else {
                                it.message?.let{ error ->
                                    showError(error)
                                }
                            }
                        }
                        ResultState.LOADING -> {
                            showLoading(true)
                        }
                        ResultState.EXCEPTION -> {
                            showLoading(false)
                            it.message?.let { it2 ->
                                showConnectivityError()
                            }
                        }
                    }
                }
            }
        }
    }
    fun onInstitutionAccountVerified() : MutableLiveData<Boolean> {
        return institutionAccountVerificationCodeLiveData
    }

    fun submitAccountVerificationCode(aggregator : Int, securityAnswer: SecurityAnswer, institutionId : String) {
        viewModelCoroutineScope.launch(IO) {
            institutionUseCase.submitAccountVerificationCode(aggregator,securityAnswer,institutionId).collect {
                withContext(Main) {
                    when(it.status){
                        ResultState.SUCCESS -> {
                            showLoading(false)
                            institutionAccountVerificationCodeLiveData.value = true
                        }
                        ResultState.ERROR -> {
                            showLoading(false)
                            if(it.kairaAction != null){
                                if(it.kairaAction == KairaAction.UNAUTHORIZED_REDIRECT){
                                    institutionUseCase.deleteToken()
                                }
                                it.kairaAction?.let{ it2 ->
                                    errorAction(ErrorAction(it.message.toString(),it2))
                                }
                            }else {
                                it.message?.let{ error ->
                                    showError(error)
                                }
                            }
                        }
                        ResultState.LOADING -> {
                            showLoading(true)
                        }
                        ResultState.EXCEPTION -> {
                            showLoading(false)
                            it.message?.let { it2 ->
                                showConnectivityError()
                            }
                        }
                    }
                }

            }
        }

    }
    fun getAllInstitutions(locale:String):ArrayList<Institution>{
        return institutionUseCase.getAllInstitutions(locale)
    }


    fun onInstitutionConnected():MediatorLiveData<Boolean>{
        return connectInstitutionLiveData
    }
    fun connectInstitution(institutionParamBody: InstitutionParamBody){
        val liveDataSource = institutionUseCase.connectInstitution(institutionParamBody)
        connectInstitutionLiveData.addSource(liveDataSource){
            when(it.status){
                ResultState.SUCCESS ->{
                    connectInstitutionLiveData.removeSource(liveDataSource)
                    connectInstitutionLiveData.value = true
                    showLoading(false)
                }
                ResultState.ERROR ->{
                    connectInstitutionLiveData.removeSource(liveDataSource)
                    connectInstitutionLiveData.value = false
                    showLoading(false)
                    if(it.kairaAction != null){
                        if(it.kairaAction == KairaAction.UNAUTHORIZED_REDIRECT){
                            institutionUseCase.deleteToken()
                        }
                        it.kairaAction?.let{ it2 ->
                            errorAction(ErrorAction(it.message.toString(),it2))
                        }
                    }else {
                        it.message?.let{ error ->
                            showError(error)
                        }
                    }
                }
                ResultState.EXCEPTION ->{
                    it.message?.let { it2->
                        showConnectivityError()
                        showLoading(false)
                    }
                    connectInstitutionLiveData.value = false
                    connectInstitutionLiveData.removeSource(liveDataSource)
                }
                ResultState.LOADING->{
                    showLoading(true)
                }
            }
        }
    }
}