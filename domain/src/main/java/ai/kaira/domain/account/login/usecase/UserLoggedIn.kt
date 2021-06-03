package ai.kaira.domain.account.login.usecase

import ai.kaira.domain.KairaResult
import ai.kaira.domain.account.create.EmailBody
import ai.kaira.domain.account.login.repository.LoginRepository
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class UserLoggedIn @Inject constructor(private val loginRepository: LoginRepository) {
    operator fun invoke(): Boolean{
        return userLoggedIn()
    }

    private fun userLoggedIn ():Boolean {
        return loginRepository.isLoggedIn()
    }
}