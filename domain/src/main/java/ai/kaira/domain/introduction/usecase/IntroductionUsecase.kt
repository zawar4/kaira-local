package ai.kaira.domain.introduction.usecase

import ai.kaira.domain.Result
import ai.kaira.domain.introduction.model.User
import ai.kaira.domain.introduction.repository.IntroductionRepository
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class CreateUserUsecase @Inject constructor(private val introductionRepository: IntroductionRepository) {

    fun createUser(firstName:String, languageLocale: String) : MutableLiveData<Result<User>>{
        return introductionRepository.createUser(firstName,languageLocale)
    }
}