package ai.kaira.domain.introduction.repository


import ai.kaira.domain.Result
import ai.kaira.domain.introduction.model.User
import androidx.lifecycle.MutableLiveData

interface IntroductionRepository {
    fun createUser(firstName:String,languageLocale:String): MutableLiveData<Result<User>>
    fun saveUser(user: User)
}