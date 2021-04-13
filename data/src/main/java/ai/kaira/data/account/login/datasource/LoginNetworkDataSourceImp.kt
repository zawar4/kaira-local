package ai.kaira.data.account.login.datasource

import ai.kaira.data.introduction.model.UserResponse
import ai.kaira.domain.account.login.LoginBody
import ai.kaira.data.webservice.KairaApiRouter
import ai.kaira.domain.KairaResult
import ai.kaira.domain.introduction.model.User
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginNetworkDataSourceImp @Inject constructor(private val kairaApiRouter: KairaApiRouter, private val viewModelCoroutineScope: CoroutineScope) : LoginNetworkDataSource {

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
                        val error: String? = response.errorBody()?.string()
                        error?.let{
                            loginLiveData.value = KairaResult.error(message = error)
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
}