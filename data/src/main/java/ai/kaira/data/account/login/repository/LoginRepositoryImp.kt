package ai.kaira.data.account.login.repository

import ai.kaira.data.account.login.datasource.local.LoginLocalDataSource
import ai.kaira.domain.account.create.EmailBody
import ai.kaira.data.account.login.datasource.network.LoginNetworkDataSource
import ai.kaira.domain.KairaResult
import ai.kaira.domain.account.login.LoginBody
import ai.kaira.domain.account.login.ResetPasswordBody
import ai.kaira.domain.account.login.repository.LoginRepository
import ai.kaira.domain.introduction.model.User
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class LoginRepositoryImp @Inject constructor(private val loginNetworkDataSource: LoginNetworkDataSource, private val loginLocalDataSource: LoginLocalDataSource) : LoginRepository {

    override fun login(loginBody: LoginBody): MutableLiveData<KairaResult<User>> {
        return loginNetworkDataSource.login(loginBody)
    }

    override fun forgotPassword(email: String,token:String):MutableLiveData<KairaResult<EmailBody>>{
        return loginNetworkDataSource.forgotPassword(email,token)
    }

    override fun resetPassword(resetPasswordBody: ResetPasswordBody): MutableLiveData<KairaResult<Unit>> {
        return loginNetworkDataSource.resetPassword(resetPasswordBody)
    }

    override fun saveToken(user: User) {
        loginLocalDataSource.saveToken(user)
    }

    override fun isLoggedIn(): Boolean {
        return loginLocalDataSource.isLoggedIn()
    }

    override fun deleteToken() {
        loginLocalDataSource.deleteToken()
    }

    override fun logout() {
        loginLocalDataSource.logout()
    }

}