package ai.kaira.domain.account.login.repository

import ai.kaira.domain.KairaResult
import ai.kaira.domain.account.create.EmailBody
import ai.kaira.domain.account.login.LoginBody
import ai.kaira.domain.introduction.model.User
import androidx.lifecycle.MutableLiveData

interface LoginRepository {

    fun login(loginBody:LoginBody) : MutableLiveData<KairaResult<User>>
    fun forgotPassword(emailBody: EmailBody):MutableLiveData<KairaResult<EmailBody>>
}