package ai.kaira.data.account.login.repository

import ai.kaira.domain.account.create.EmailBody
import ai.kaira.data.account.login.datasource.LoginNetworkDataSource
import ai.kaira.domain.KairaResult
import ai.kaira.domain.account.login.LoginBody
import ai.kaira.domain.account.login.repository.LoginRepository
import ai.kaira.domain.introduction.model.User
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class LoginRepositoryImp @Inject constructor(private val loginNetworkDataSource: LoginNetworkDataSource) : LoginRepository {

    override fun login(loginBody: LoginBody): MutableLiveData<KairaResult<User>> {
        return loginNetworkDataSource.login(loginBody)
    }

    override fun forgotPassword(emailBody: EmailBody):MutableLiveData<KairaResult<Void>>{
        return loginNetworkDataSource.forgotPassword(emailBody)
    }

}