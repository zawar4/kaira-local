package ai.kaira.data.account.datasource.network

import ai.kaira.data.webservice.KairaApiRouter
import ai.kaira.domain.Result
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
    override fun groupCodeExists(groupCode: String): MutableLiveData<Result<Boolean>> {
        val groupCodeExistsLiveData = MutableLiveData<Result<Boolean>>()
        viewModelCoroutineScope.launch(IO){
            withContext(Main){
                groupCodeExistsLiveData.value = Result.loading()
            }
            val response = kairaApiRouter.groupCodeExists(groupCode).execute()
            withContext(Main){
                if(response.isSuccessful){
                    groupCodeExistsLiveData.value = Result.success(true)
                } else {
                    val error : String? = response.errorBody()?.string()
                    error?.let{
                        groupCodeExistsLiveData.value = Result.error(false,error)
                    }

                }
            }

        }
        return groupCodeExistsLiveData
    }

    override fun emailExists(email: String): MutableLiveData<Result<Boolean>> {
        val emailExistsLiveData = MutableLiveData<Result<Boolean>>()
        viewModelCoroutineScope.launch(IO) {
            withContext(Main){
                emailExistsLiveData.value = Result.loading()
            }
            val response = kairaApiRouter.emailExists(email).execute()
            withContext(Main){
                if(response.isSuccessful){
                    emailExistsLiveData.value = Result.success(response.body())
                }else{
                    val error : String? = response.errorBody()?.string()
                    error?.let{
                        emailExistsLiveData.value = Result.error(message =error)
                    }
                }
            }
        }
        return emailExistsLiveData
    }

    override fun createAccount(accountDetails: Account): MutableLiveData<Result<User>> {
        val createAccountLiveData = MutableLiveData<Result<User>>()
        viewModelCoroutineScope.launch(IO) {
            withContext(Main){
                createAccountLiveData.value = Result.loading()
            }
            val response = kairaApiRouter.createAccount(accountDetails).execute()
            withContext(Main){
                if(response.isSuccessful){
                    response.body().let{ user ->
                        createAccountLiveData.value = Result.success(user)
                    }
                }else{
                    val error : String? = response.errorBody()?.string()
                    error?.let{
                        createAccountLiveData.value = Result.error(message = error)
                    }
                }
            }
        }
        return createAccountLiveData
    }
}