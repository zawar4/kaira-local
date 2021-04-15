package ai.kaira.app.account.login.viewmodel

import ai.kaira.app.application.BaseViewModel
import ai.kaira.domain.KairaResult
import ai.kaira.domain.ResultState
import ai.kaira.domain.account.create.EmailBody
import ai.kaira.domain.account.login.LoginBody
import ai.kaira.domain.account.login.usecase.LoginUseCase
import ai.kaira.domain.introduction.model.User
import android.util.Patterns
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

class LoginViewModel constructor(private val loginUseCase: LoginUseCase) : BaseViewModel() {

    private val loginLiveData = MediatorLiveData<User>()
    private val forgotPasswordLiveData = MediatorLiveData<Void>()

    fun forgotPassword(email: String){
        val liveDataSource = loginUseCase.forgotPassword(EmailBody(email))
        forgotPasswordLiveData.addSource(liveDataSource){ result ->
            when(result.status){
                ResultState.SUCCESS ->{
                    showLoading(false)
                    forgotPasswordLiveData.removeSource(liveDataSource)
                }
                ResultState.LOADING ->{
                    showLoading(true)
                }
                ResultState.ERROR ->{
                    result.message?.let{ error ->
                        showError(error)
                    }
                    showLoading(false)
                    forgotPasswordLiveData.removeSource(liveDataSource)
                }
                ResultState.EXCEPTION ->{
                    showConnectivityError()
                    showLoading(false)
                    forgotPasswordLiveData.removeSource(liveDataSource)
                }
            }

        }
    }

    fun onForgotPassword():MediatorLiveData<Void>{
        return forgotPasswordLiveData
    }
    fun login(email:String,password:String){
        val liveDataSource = loginUseCase.login(LoginBody(email,password))
        loginLiveData.addSource(liveDataSource){ result ->
            when(result.status){
                ResultState.SUCCESS ->{
                    showLoading(false)
                    loginLiveData.removeSource(liveDataSource)
                }
                ResultState.LOADING ->{
                    showLoading(true)
                }
                ResultState.ERROR ->{
                    result.message?.let{ error ->
                        showError(error)
                    }
                    showLoading(false)
                    loginLiveData.removeSource(liveDataSource)
                }
                ResultState.EXCEPTION ->{
                    showConnectivityError()
                    showLoading(false)
                    loginLiveData.removeSource(liveDataSource)
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