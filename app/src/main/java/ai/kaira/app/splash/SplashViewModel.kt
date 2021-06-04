package ai.kaira.app.splash

import ai.kaira.app.application.BaseViewModel
import ai.kaira.domain.ErrorAction
import ai.kaira.domain.KairaAction
import ai.kaira.domain.ResultState
import ai.kaira.domain.splash.usecase.SplashUseCase
import androidx.lifecycle.MediatorLiveData

class SplashViewModel constructor(private val splashUseCase: SplashUseCase):BaseViewModel() {

    private val institutionFetchedLiveData = MediatorLiveData<Boolean>()
    fun isLoggedIn():Boolean{
        return splashUseCase.userLoggedIn()
    }

    fun onInstitutionFetched():MediatorLiveData<Boolean>{
        return institutionFetchedLiveData
    }

    fun getMyInstitutions(){
        val liveDataSource = splashUseCase.getMyInstitutions()
        institutionFetchedLiveData.addSource(liveDataSource) {
            when (it.status) {
                ResultState.SUCCESS -> {
                    if(it.data != null){
                        institutionFetchedLiveData.value =  it.data!!.isNotEmpty()
                    }
                    institutionFetchedLiveData.removeSource(liveDataSource)
                    showLoading(false)
                }
                ResultState.ERROR -> {
                    institutionFetchedLiveData.removeSource(liveDataSource)
                    institutionFetchedLiveData.value = false
                    showLoading(false)
                    if (it.kairaAction != null) {
                        if(it.kairaAction == KairaAction.UNAUTHORIZED_REDIRECT){
                            splashUseCase.deleteToken()
                        }
                        it.kairaAction?.let { it2 ->
                            errorAction(ErrorAction(it.message.toString(), it2))
                        }
                    } else {
                        it.message?.let { error ->
                            showError(error)
                        }
                    }
                }
                ResultState.EXCEPTION -> {
                    it.message?.let { it2 ->
                        showConnectivityError()
                        showLoading(false)
                    }
                    institutionFetchedLiveData.value = false
                    institutionFetchedLiveData.removeSource(liveDataSource)
                }
                ResultState.LOADING -> {
                    showLoading(true)
                }
            }
        }
    }
}