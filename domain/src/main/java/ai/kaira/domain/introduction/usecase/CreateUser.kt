package ai.kaira.domain.introduction.usecase

import ai.kaira.domain.KairaResult
import ai.kaira.domain.introduction.model.User
import ai.kaira.domain.introduction.repository.IntroductionRepository
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class CreateUser @Inject constructor(private val introductionRepository: IntroductionRepository) {

    operator fun invoke(firstName:String, languageLocale: String):MutableLiveData<KairaResult<User>>{
        return createUser(firstName,languageLocale)
    }
    fun createUser(firstName:String, languageLocale: String) : MutableLiveData<KairaResult<User>> {
        return introductionRepository.createUser(firstName,languageLocale)
    }
}