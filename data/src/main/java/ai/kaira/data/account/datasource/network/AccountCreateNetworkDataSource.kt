package ai.kaira.data.account.datasource.network

import ai.kaira.domain.KairaResult
import ai.kaira.domain.account.model.Account
import ai.kaira.domain.introduction.model.User
import androidx.lifecycle.MutableLiveData

interface AccountCreateNetworkDataSource {

    fun groupCodeExists(groupCode:String): MutableLiveData<KairaResult<Boolean>>
    fun emailExists(email:String): MutableLiveData<KairaResult<Boolean>>
    fun createAccount(accountDetails: Account): MutableLiveData<KairaResult<User>>
    fun sendVerificationEmail(email:String):MutableLiveData<KairaResult<Void>>
    fun verifyAccount(token:String):MutableLiveData<KairaResult<Void>>
}