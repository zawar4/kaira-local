package ai.kaira.domain.introduction.usecase

import ai.kaira.domain.introduction.model.User
import ai.kaira.domain.introduction.repository.IntroductionRepository
import javax.inject.Inject

class SaveUser @Inject constructor(private val introductionRepository: IntroductionRepository) {

    operator fun invoke(user: User){
        saveUser(user)
    }
    fun saveUser(user: User){
        introductionRepository.saveUser(user)
    }
}