package ai.kaira.domain.account.repository

import ai.kaira.domain.Result
import ai.kaira.domain.account.model.Account
import ai.kaira.domain.introduction.model.User
import androidx.lifecycle.MutableLiveData

interface AccountCreateRepository {
    fun groupCodeExists(groupCode:String): MutableLiveData<Result<Boolean>>
    fun emailExists(email: String): MutableLiveData<Result<Boolean>>
    fun createAccount(accountDetails: Account): MutableLiveData<Result<User>>
    fun sendVerificationEmail(email: String): MutableLiveData<Result<Void>>
}