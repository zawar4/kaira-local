package ai.kaira.domain.introduction.repository

import ai.kaira.domain.introduction.model.User
import androidx.lifecycle.MutableLiveData

interface IntroductionRepository {
    fun createUser(firstName:String,languageLocale:String): MutableLiveData<User>
    fun saveUser(user: User):MutableLiveData<Boolean>
}