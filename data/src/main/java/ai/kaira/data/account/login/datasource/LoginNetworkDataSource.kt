package ai.kaira.data.account.login.datasource

import ai.kaira.domain.account.create.EmailBody
import ai.kaira.domain.account.login.LoginBody
import ai.kaira.domain.KairaResult
import ai.kaira.domain.introduction.model.User
import androidx.lifecycle.MutableLiveData

interface LoginNetworkDataSource {

    fun login(loginBody: LoginBody):MutableLiveData<KairaResult<User>>
    fun forgotPassword(emailBody: EmailBody):MutableLiveData<KairaResult<EmailBody>>
}