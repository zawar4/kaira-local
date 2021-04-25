package ai.kaira.domain.account.login.usecase

import ai.kaira.domain.account.login.repository.LoginRepository
import ai.kaira.domain.introduction.model.User
import javax.inject.Inject

class SaveToken @Inject constructor(private val loginRepository: LoginRepository) {

    operator fun invoke(user:User){
        saveToken(user)
    }

    fun saveToken(user:User){
        loginRepository.saveToken(user)
    }

}