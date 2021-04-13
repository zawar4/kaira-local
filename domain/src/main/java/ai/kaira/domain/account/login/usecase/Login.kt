package ai.kaira.domain.account.login.usecase

import ai.kaira.domain.KairaResult
import ai.kaira.domain.account.login.LoginBody
import ai.kaira.domain.account.login.repository.LoginRepository
import ai.kaira.domain.introduction.model.User
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class Login @Inject constructor(private val loginRepository: LoginRepository) {

    operator fun invoke(loginBody: LoginBody):MutableLiveData<KairaResult<User>>{
        return login(loginBody)
    }

    fun login(loginBody: LoginBody):MutableLiveData<KairaResult<User>>{
        return loginRepository.login(loginBody)
    }
}