package ai.kaira.data.account.login.datasource.network

import ai.kaira.domain.account.create.EmailBody
import ai.kaira.data.introduction.model.UserResponse
import ai.kaira.domain.account.login.LoginBody
import ai.kaira.data.webservice.KairaApiRouter
import ai.kaira.domain.KairaAction
import ai.kaira.domain.KairaResult
import ai.kaira.domain.account.create.TokenBody
import ai.kaira.domain.account.login.ResetPasswordBody
import ai.kaira.domain.introduction.model.User
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class LoginNetworkDataSourceImp @Inject constructor(private val kairaApiRouter: KairaApiRouter, private val viewModelCoroutineScope: CoroutineScope) :
    LoginNetworkDataSource {

    override fun login(loginBody: LoginBody): MutableLiveData<KairaResult<User>> {
        val loginLiveData = MutableLiveData<KairaResult<User>>()
        viewModelCoroutineScope.launch(IO) {
            withContext(Dispatchers.Main){
                loginLiveData.value = KairaResult.loading()
            }
            try {
                val response = kairaApiRouter.login(loginBody).execute()
                withContext(Main){
                    if(response.isSuccessful) {
                        val body : UserResponse? = response.body()
                        loginLiveData.value = KairaResult.success(data = body?.maptoUser())
                    } else {
                        if(response.code() == 403){
                            val error: String? = response.errorBody()?.string()
                            error?.let{
                                loginLiveData.value = KairaResult.error(message = error,kairaAction = KairaAction.UNVERIFIED_REDIRECT)
                            }
                        }else {
                            val error: String? = response.errorBody()?.string()
                            error?.let{
                                loginLiveData.value = KairaResult.error(message = error)
                            }
                        }
                    }
                }
            }
            catch (exception :Exception) {
                withContext(Dispatchers.Main) {
                    exception.message?.let { message ->
                        loginLiveData.value = KairaResult.exception(message = message)
                    }
                }
                exception.printStackTrace()
            }
        }
        return loginLiveData
    }

    override fun forgotPassword(email: String,token:String):MutableLiveData<KairaResult<EmailBody>>{
        val forgotPasswordLiveData = MutableLiveData<KairaResult<EmailBody>>()
        viewModelCoroutineScope.launch(IO) {
            withContext(Main){
                forgotPasswordLiveData.value = KairaResult.loading()
            }
            try{
                var response : Response<EmailBody> = if(token != null && token.isNotBlank()){
                    kairaApiRouter.forgotPassword(TokenBody(token)).execute()
                }else{
                    kairaApiRouter.forgotPassword(EmailBody(email)).execute()
                }
                withContext(Main){
                    if(response.isSuccessful){
                        val emailBody = response.body()
                        forgotPasswordLiveData.value = KairaResult.success(emailBody)
                    }else{
                        val error: String? = response.errorBody()?.string()
                        error?.let{
                            forgotPasswordLiveData.value = KairaResult.error(message = error)
                        }
                    }
                }
            }
            catch (exception : Exception){
                withContext(Dispatchers.Main) {
                    exception.message?.let { message ->
                        forgotPasswordLiveData.value = KairaResult.exception(message = message)
                    }
                }
                exception.printStackTrace()
            }
        }
        return forgotPasswordLiveData
    }

    override fun resetPassword(resetPasswordBody: ResetPasswordBody): MutableLiveData<KairaResult<Unit>> {
        val resetPasswordLiveData = MutableLiveData<KairaResult<Unit>>()
        viewModelCoroutineScope.launch(IO) {
            withContext(Main){
                resetPasswordLiveData.value = KairaResult.loading()
            }
            try{
                val response = kairaApiRouter.resetPassword(resetPasswordBody).execute()
                withContext(Main){
                    if(response.isSuccessful){
                        resetPasswordLiveData.value = KairaResult.success()
                    }else{
                        if(response.code() == 403){
                            val error: String? = response.errorBody()?.string()
                            error?.let{
                                resetPasswordLiveData.value = KairaResult.error(message = error,kairaAction = KairaAction.TOKEN_EXPIRED_REDIRECT)
                            }
                        }else {
                            val error: String? = response.errorBody()?.string()
                            error?.let{
                                resetPasswordLiveData.value = KairaResult.error(message = error)
                            }
                        }
                    }
                }
            }
            catch (exception : Exception){
                withContext(Dispatchers.Main) {
                    exception.message?.let { message ->
                        resetPasswordLiveData.value = KairaResult.exception(message = message)
                    }
                }
                exception.printStackTrace()
            }
        }
        return resetPasswordLiveData
    }
}