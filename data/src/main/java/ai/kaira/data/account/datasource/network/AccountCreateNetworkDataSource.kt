package ai.kaira.data.account.datasource.network

import ai.kaira.domain.Result
import ai.kaira.domain.account.model.Account
import ai.kaira.domain.introduction.model.User
import androidx.lifecycle.MutableLiveData

interface AccountCreateNetworkDataSource {

    fun groupCodeExists(groupCode:String): MutableLiveData<Result<Boolean>>
    fun emailExists(email:String): MutableLiveData<Result<Boolean>>
    fun createAccount(accountDetails: Account): MutableLiveData<Result<User>>
}