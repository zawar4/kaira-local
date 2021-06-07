package ai.kaira.domain.account.login.usecase

import ai.kaira.domain.KairaResult
import ai.kaira.domain.account.create.EmailBody
import ai.kaira.domain.account.login.repository.LoginRepository
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class ForgotPassword @Inject constructor(private val loginRepository: LoginRepository) {

    operator fun invoke(email: String,token:String):MutableLiveData<KairaResult<EmailBody>>{
        return forgotPassword(email,token)
    }

    fun forgotPassword(email: String,token:String):MutableLiveData<KairaResult<EmailBody>>{
        return loginRepository.forgotPassword(email,token)
    }
}