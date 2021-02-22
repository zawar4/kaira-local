package ai.kaira.domain.introduction.usecase

import ai.kaira.domain.introduction.model.User
import ai.kaira.domain.introduction.repository.IntroductionRepository
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class FetchUser @Inject constructor(private val introductionRepository: IntroductionRepository) {

    operator fun invoke (): User {
        return fetchUser()
    }
    fun fetchUserAsync(): MutableLiveData<User?> {
        return introductionRepository.fetchUserAsync()
    }

    fun fetchUser(): User {
        return introductionRepository.fetchUser()
    }
}