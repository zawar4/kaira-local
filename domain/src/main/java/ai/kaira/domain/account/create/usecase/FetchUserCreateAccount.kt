package ai.kaira.domain.account.create.usecase

import ai.kaira.domain.KairaResult
import ai.kaira.domain.ResultState
import ai.kaira.domain.account.create.model.Account
import ai.kaira.domain.introduction.model.User
import ai.kaira.domain.introduction.usecase.FetchUser
import androidx.lifecycle.MediatorLiveData
import javax.inject.Inject

class FetchUserCreateAccount @Inject constructor(val createAccount:CreateAccount,val fetchUser: FetchUser) {

    private val createAccountLiveData = MediatorLiveData<KairaResult<User>>()
    operator fun invoke(firstName:String,lastName:String,language:String,email:String,password:String,groupCode:String,bankingAggregator:Int): MediatorLiveData<KairaResult<User>> {
        return fetchUserCreateAccount(firstName, lastName, language, email, password, groupCode,bankingAggregator = bankingAggregator)
    }

    fun fetchUserCreateAccount(firstName:String,lastName:String,language:String,email:String,password:String,groupCode:String,bankingAggregator:Int): MediatorLiveData<KairaResult<User>> {
        val liveDataSource = fetchUser.fetchUserAsync()
        createAccountLiveData.addSource(liveDataSource){ user ->
            createAccountLiveData.removeSource(liveDataSource)
            val userId = user?.id ?: ""
            var accountDetails = Account(firstName = firstName,lastName = lastName,language = language,email = email, password = password, groupCode = groupCode,id = userId, bankingAggregator = bankingAggregator)
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
                    ResultState.EXCEPTION ->{
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