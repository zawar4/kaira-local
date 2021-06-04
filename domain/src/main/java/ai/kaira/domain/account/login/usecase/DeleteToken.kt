package ai.kaira.domain.account.login.usecase

import ai.kaira.domain.account.login.repository.LoginRepository
import javax.inject.Inject

class DeleteToken @Inject constructor(private val loginRepository: LoginRepository) {

    operator fun invoke(){
        deleteToken()
    }

    fun deleteToken(){
        loginRepository.deleteToken()
    }
}