package ai.kaira.domain.introduction.repository


import ai.kaira.domain.KairaResult
import ai.kaira.domain.introduction.model.User
import androidx.lifecycle.MutableLiveData

interface IntroductionRepository {
    fun createUser(firstName:String,languageLocale:String): MutableLiveData<KairaResult<User>>
    fun saveUser(user: User)
    fun fetchUser():User?
    fun fetchUserAsync(): MutableLiveData<User?>
}