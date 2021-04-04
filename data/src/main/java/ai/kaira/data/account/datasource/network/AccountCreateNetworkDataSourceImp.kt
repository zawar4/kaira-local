package ai.kaira.data.account.datasource.network

import ai.kaira.data.account.EmailBody
import ai.kaira.data.webservice.KairaApiRouter
import ai.kaira.domain.KairaResult
import ai.kaira.domain.account.model.Account
import ai.kaira.domain.introduction.model.User
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountCreateNetworkDataSourceImp @Inject constructor(private val kairaApiRouter: KairaApiRouter,private val viewModelCoroutineScope: CoroutineScope) : AccountCreateNetworkDataSource {
    override fun groupCodeExists(groupCode: String): MutableLiveData<KairaResult<Boolean>> {
        val groupCodeExistsLiveData = MutableLiveData<KairaResult<Boolean>>()
        viewModelCoroutineScope.launch(IO){
            withContext(Main){
                groupCodeExistsLiveData.value = KairaResult.loading()
            }
            try {
                val response = kairaApiRouter.groupCodeExists(groupCode).execute()
                withContext(Main) {
                    if (response.isSuccessful) {
                        groupCodeExistsLiveData.value = KairaResult.success(true)
                    } else {
                        val error: String? = response.errorBody()?.string()
                        error?.let {
                            if(error.isEmpty()){
                                groupCodeExistsLiveData.value = KairaResult.success(false)
                            }else{
                                groupCodeExistsLiveData.value = KairaResult.error(false, error)
                            }
                        }

                    }
                }
            }
            catch(exception:Exception){
                withContext(Main) {
                    exception.message?.let { message ->
                        groupCodeExistsLiveData.value = KairaResult.exception(message = message)
                    }
                }
                exception.printStackTrace()
            }


        }
        return groupCodeExistsLiveData
    }

    override fun emailExists(email: String): MutableLiveData<KairaResult<Boolean>> {
        val emailExistsLiveData = MutableLiveData<KairaResult<Boolean>>()
        viewModelCoroutineScope.launch(IO) {
            withContext(Main){
                emailExistsLiveData.value = KairaResult.loading()
            }
            try {
                val response = kairaApiRouter.emailExists(email).execute()
                withContext(Main) {
                    if (response.isSuccessful) {
                        emailExistsLiveData.value = KairaResult.success(response.body())
                    } else {
                        val error: String? = response.errorBody()?.string()
                        error?.let {
                            emailExistsLiveData.value = KairaResult.error(message = error)
                        }
                    }
                }
            }
            catch(exception:Exception){
                withContext(Main) {
                    exception.message?.let { message ->
                        emailExistsLiveData.value = KairaResult.exception(message = message)
                    }
                }
                exception.printStackTrace()
            }
        }
        return emailExistsLiveData
    }

    override fun createAccount(accountDetails: Account): MutableLiveData<KairaResult<User>> {
        val createAccountLiveData = MutableLiveData<KairaResult<User>>()
        viewModelCoroutineScope.launch(IO) {
            withContext(Main){
                createAccountLiveData.value = KairaResult.loading()
            }
            try {
                val response = kairaApiRouter.createAccount(accountDetails).execute()
                withContext(Main) {
                    if (response.isSuccessful) {
                        response.body().let { user ->
                            createAccountLiveData.value = KairaResult.success(user)
                        }
                    } else {
                        val error: String? = response.errorBody()?.string()
                        error?.let {
                            createAccountLiveData.value = KairaResult.error(message = error)
                        }
                    }
                }
            }
            catch(exception:Exception){
                withContext(Main) {
                    exception.message?.let { message ->
                        createAccountLiveData.value = KairaResult.exception(message = message)
                    }
                }
                exception.printStackTrace()
            }
        }
        return createAccountLiveData
    }

    override fun sendVerificationEmail(email: String): MutableLiveData<KairaResult<Void>> {
        val sendVerificationEmailLiveData = MutableLiveData<KairaResult<Void>>()
        viewModelCoroutineScope.launch(IO) {
            withContext(Main){
                sendVerificationEmailLiveData.value = KairaResult.loading()
            }
            try
            {
                val response = kairaApiRouter.sendVerificationEmail(EmailBody(email)).execute()
                withContext(Main){
                    if(response.isSuccessful){
                        sendVerificationEmailLiveData.value = KairaResult.success()
                    }else{
                        val error : String? = response.errorBody()?.string()
                        error?.let{
                            sendVerificationEmailLiveData.value = KairaResult.error(message = error)
                        }
                    }
                }
            }
            catch(exception:Exception){
                withContext(Main) {
                    exception.message?.let { message ->
                        sendVerificationEmailLiveData.value =
                            KairaResult.exception(message = message)
                    }
                }
                exception.printStackTrace()
            }

        }
        return sendVerificationEmailLiveData
    }
}