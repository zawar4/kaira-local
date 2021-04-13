package ai.kaira.app.account.login.viewmodel

import ai.kaira.app.application.BaseViewModel
import ai.kaira.domain.KairaResult
import ai.kaira.domain.ResultState
import ai.kaira.domain.account.login.LoginBody
import ai.kaira.domain.account.login.usecase.LoginUseCase
import ai.kaira.domain.introduction.model.User
import android.util.Patterns
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

class LoginViewModel constructor(private val loginUseCase: LoginUseCase) : BaseViewModel() {

    private val loginLiveData = MediatorLiveData<User>()

    fun login(email:String,password:String){
        val liveDataSource = loginUseCase.login(LoginBody(email,password))
        loginLiveData.addSource(liveDataSource){ result ->
            when(result.status){
                ResultState.SUCCESS ->{

                    showLoading(false)
                    loginLiveData.removeSource(loginLiveData)
                }
                ResultState.LOADING ->{

                    showLoading(true)
                }
                ResultState.ERROR ->{

                    result.message?.let{ error ->
                        showError(error)
                    }
                    showLoading(false)
                    loginLiveData.removeSource(loginLiveData)
                }
                ResultState.EXCEPTION ->{
                    showConnectivityError()
                    showLoading(false)
                    loginLiveData.removeSource(loginLiveData)
                }
            }
        }
    }

    fun onUserLoggedIn(): MediatorLiveData<User>{
        return loginLiveData
    }

    fun isValidEmail(target: String): Boolean {
        return if(target.isBlank()){
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }
    fun isValidPassword(password:String):Boolean {
        return password.isNotBlank()
    }
}