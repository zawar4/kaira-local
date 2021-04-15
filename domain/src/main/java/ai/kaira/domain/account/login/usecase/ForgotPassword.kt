package ai.kaira.domain.account.login.usecase

import ai.kaira.domain.KairaResult
import ai.kaira.domain.account.create.EmailBody
import ai.kaira.domain.account.login.repository.LoginRepository
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class ForgotPassword @Inject constructor(private val loginRepository: LoginRepository) {

    operator fun invoke(emailBody: EmailBody):MutableLiveData<KairaResult<EmailBody>>{
        return forgotPassword(emailBody)
    }

    fun forgotPassword(emailBody: EmailBody):MutableLiveData<KairaResult<EmailBody>>{
        return loginRepository.forgotPassword(emailBody)
    }
}