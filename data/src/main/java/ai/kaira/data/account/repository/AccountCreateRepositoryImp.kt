package ai.kaira.data.account.repository

import ai.kaira.data.account.datasource.network.AccountCreateNetworkDataSource
import ai.kaira.domain.KairaResult
import ai.kaira.domain.account.model.Account
import ai.kaira.domain.account.repository.AccountCreateRepository
import ai.kaira.domain.introduction.model.User
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class AccountCreateRepositoryImp @Inject constructor(private val accountCreateNetworkDataSource: AccountCreateNetworkDataSource) : AccountCreateRepository {

    override fun groupCodeExists(groupCode: String): MutableLiveData<KairaResult<Boolean>> {
        return accountCreateNetworkDataSource.groupCodeExists(groupCode)
    }

    override fun emailExists(email: String): MutableLiveData<KairaResult<Boolean>> {
        return accountCreateNetworkDataSource.emailExists(email)
    }

    override fun createAccount(accountDetails: Account): MutableLiveData<KairaResult<User>> {
        return accountCreateNetworkDataSource.createAccount(accountDetails)
    }

    override fun sendVerificationEmail(email: String): MutableLiveData<KairaResult<Void>> {
        return accountCreateNetworkDataSource.sendVerificationEmail(email)
    }
}