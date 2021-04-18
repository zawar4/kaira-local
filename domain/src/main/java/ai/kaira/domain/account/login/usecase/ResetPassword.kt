package ai.kaira.domain.account.login.usecase

import ai.kaira.domain.KairaResult
import ai.kaira.domain.account.create.EmailBody
import ai.kaira.domain.account.login.ResetPasswordBody
import ai.kaira.domain.account.login.repository.LoginRepository
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class ResetPassword @Inject constructor(private val loginRepository: LoginRepository) {

    operator fun invoke(resetPasswordBody: ResetPasswordBody): MutableLiveData<KairaResult<Unit>> {
        return forgotPassword(resetPasswordBody)
    }

    fun forgotPassword(resetPasswordBody: ResetPasswordBody): MutableLiveData<KairaResult<Unit>> {
        return loginRepository.resetPassword(resetPasswordBody)
    }
}