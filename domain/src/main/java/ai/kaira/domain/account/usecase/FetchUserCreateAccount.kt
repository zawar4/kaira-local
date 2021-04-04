package ai.kaira.domain.account.usecase

import ai.kaira.domain.Result
import ai.kaira.domain.ResultState
import ai.kaira.domain.account.model.Account
import ai.kaira.domain.introduction.model.User
import ai.kaira.domain.introduction.usecase.FetchUser
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class FetchUserCreateAccount @Inject constructor(val createAccount:CreateAccount,val fetchUser: FetchUser) {

    private val createAccountLiveData = MediatorLiveData<Result<User>>()
    operator fun invoke(firstName:String,lastName:String,language:String,email:String,password:String,groupCode:String): MediatorLiveData<Result<User>> {
        return fetchUserCreateAccount(firstName, lastName, language, email, password, groupCode)
    }

    fun fetchUserCreateAccount(firstName:String,lastName:String,language:String,email:String,password:String,groupCode:String): MediatorLiveData<Result<User>> {
        val liveDataSource = fetchUser.fetchUserAsync()
        createAccountLiveData.addSource(liveDataSource){ user ->
            createAccountLiveData.removeSource(liveDataSource)
            val accountDetails = Account(firstName = firstName,lastName = lastName,language = language,email = email, password = password, groupCode = groupCode,id = user?.id.toString())
            val createAccountLiveDataSource = createAccount(accountDetails)
            createAccountLiveData.addSource(createAccountLiveDataSource){ result ->
                when(result.status){
                    ResultState.SUCCESS -> {
                        createAccountLiveData.removeSource(createAccountLiveDataSource)
                        createAccountLiveData.value = result
                    }
                    ResultState.ERROR -> {
                        createAccountLiveData.removeSource(createAccountLiveDataSource)
                        createAccountLiveData.value = result
                    }
                    ResultState.LOADING ->{
                        createAccountLiveData.value = result
                    }
                }

            }
        }
        return createAccountLiveData
    }
}