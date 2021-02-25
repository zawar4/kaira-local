package ai.kaira.data.introduction.datasource.database.source

import ai.kaira.data.introduction.datasource.database.entity.UserEntity
import ai.kaira.domain.introduction.model.User
import androidx.lifecycle.MutableLiveData

interface IntroductionLocalDataSource {
    fun insertUser(userEntity: UserEntity)
    fun fetchUser(): UserEntity
    fun fetchUserAsync(): MutableLiveData<User?>
}