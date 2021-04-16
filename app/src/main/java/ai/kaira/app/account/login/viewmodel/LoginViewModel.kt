package ai.kaira.app.account.login.viewmodel

import ai.kaira.app.application.BaseViewModel
import ai.kaira.domain.KairaResult
import ai.kaira.domain.ResultState
import ai.kaira.domain.account.create.EmailBody
import ai.kaira.domain.account.login.LoginBody
import ai.kaira.domain.account.login.ResetPasswordBody
import ai.kaira.domain.account.login.usecase.LoginUseCase
import ai.kaira.domain.introduction.model.User
import android.util.Patterns
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

class LoginViewModel constructor(private val loginUseCase: LoginUseCase) : BaseViewModel() {

    private val loginLiveData = MediatorLiveData<User>()
    private val forgotPasswordLiveData = MediatorLiveData<EmailBody>()
    private val sendForgotPasswordVerificationEmailLiveData = MediatorLiveData<EmailBody>()
    private val sendVerificationEmailLiveData = MediatorLiveData<Unit>()
    private val resetPasswordLiveData = MediatorLiveData<Unit>()


    fun sendVerificationEmail(email:String){
        val liveDataSource = loginUseCase.sendVerificationEmail(email)
        sendVerificationEmailLiveData.addSource(liveDataSource){ result ->
            when(result.status){
                ResultState.SUCCESS ->{
                    showLoading(false)
                    sendVerificationEmailLiveData.removeSource(liveDataSource)
                    sendVerificationEmailLiveData.value = Unit
                }
                ResultState.ERROR ->{
                    result.message?.let{ it->
                        showError(it)
                    }
                    sendVerificationEmailLiveData.removeSource(liveDataSource)
                    showLoading(false)
                }
                ResultState.EXCEPTION ->{
                    /*result.message?.let{ it->
                        showError(it)
                    }*/
                    showConnectivityError()
                    sendVerificationEmailLiveData.removeSource(liveDataSource)
                    showLoading(false)
                }
                ResultState.LOADING ->{
                    showLoading(true)
                }
            }
        }
    }

    fun onVerificationEmailSent(): MediatorLiveData<Unit> {
        return sendVerificationEmailLiveData
    }

    fun resetPassword(password:String,token:String){
        val liveDataSource = loginUseCase.resetPassword(ResetPasswordBody(password,token))
        resetPasswordLiveData.addSource(liveDataSource){ result ->
            when(result.status){
                ResultState.SUCCESS ->{
                    showLoading(false)
                    resetPasswordLiveData.removeSource(liveDataSource)
                    resetPasswordLiveData.value = Unit
                }
                ResultState.ERROR ->{
                    result.message?.let{ it->
                        showError(it)
                    }
                    resetPasswordLiveData.removeSource(liveDataSource)
                    showLoading(false)
                }
                ResultState.EXCEPTION ->{
                    /*result.message?.let{ it->
                        showError(it)
                    }*/
                    showConnectivityError()
                    resetPasswordLiveData.removeSource(liveDataSource)
                    showLoading(false)
                }
                ResultState.LOADING ->{
                    showLoading(true)
                }
            }
        }
    }

    fun onPasswordReset(): MediatorLiveData<Unit> {
        return resetPasswordLiveData
    }


    fun sendForgotPasswordVerificationEmail(email:String){
        val liveDataSource = loginUseCase.forgotPassword(EmailBody(email))
        sendForgotPasswordVerificationEmailLiveData.addSource(liveDataSource){ result ->
            when(result.status){
                ResultState.SUCCESS ->{
                    showLoading(false)
                    sendForgotPasswordVerificationEmailLiveData.removeSource(liveDataSource)
                    sendForgotPasswordVerificationEmailLiveData.value = result.data
                }
                ResultState.ERROR ->{
                    result.message?.let{ it->
                        showError(it)
                    }
                    sendForgotPasswordVerificationEmailLiveData.removeSource(liveDataSource)
                    showLoading(false)
                }
                ResultState.EXCEPTION ->{
                    /*result.message?.let{ it->
                        showError(it)
                    }*/
                    showConnectivityError()
                    sendForgotPasswordVerificationEmailLiveData.removeSource(liveDataSource)
                    showLoading(false)
                }
                ResultState.LOADING ->{
                    showLoading(true)
                }
            }
        }
    }

    fun onForgotPasswordVerificationEmailSent(): MediatorLiveData<EmailBody> {
        return sendForgotPasswordVerificationEmailLiveData
    }
    fun forgotPassword(email: String){
        val liveDataSource = loginUseCase.forgotPassword(EmailBody(email))
        forgotPasswordLiveData.addSource(liveDataSource){ result ->
            when(result.status){
                ResultState.SUCCESS ->{
                    showLoading(false)
                    forgotPasswordLiveData.removeSource(liveDataSource)
                    forgotPasswordLiveData.value = result.data
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

    fun onForgotPassword():MediatorLiveData<EmailBody>{
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
                    if(result.kairaAction != null){
                        result.kairaAction?.let{ it ->
                            errorAction(it)
                        }
                    }else {
                        result.message?.let{ error ->
                            showError(error)
                        }
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

    fun arePasswordsSame(password1:String,password2:String): Boolean {
        return password1 == password2
    }
}