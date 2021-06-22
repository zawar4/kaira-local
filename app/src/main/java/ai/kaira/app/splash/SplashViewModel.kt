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
}