package ai.kaira.app.banking.institution.fragments.viewmodel

import ai.kaira.app.application.BaseViewModel
import ai.kaira.domain.ErrorAction
import ai.kaira.domain.KairaResult
import ai.kaira.domain.ResultState
import ai.kaira.domain.banking.institution.model.ConnectedInstitution
import ai.kaira.domain.banking.institution.model.Institution
import ai.kaira.domain.banking.institution.model.InstitutionParamBody
import ai.kaira.domain.banking.institution.usecase.ConnectInstitution
import ai.kaira.domain.banking.institution.usecase.InstitutionUseCase
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

class InstitutionViewModel(private val institutionUseCase: InstitutionUseCase) : BaseViewModel() {

    private val connectInstitutionLiveData = MediatorLiveData<Boolean>()
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
                    //TODO clear token
                    connectInstitutionLiveData.removeSource(liveDataSource)
                    connectInstitutionLiveData.value = false
                    showLoading(false)
                    if(it.kairaAction != null){
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