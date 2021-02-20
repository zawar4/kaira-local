package ai.kaira.data.introduction.datasource.database.source

import ai.kaira.data.introduction.datasource.database.entity.UserEntity
import androidx.lifecycle.MutableLiveData

interface IntroductionLocalDataSource {
    fun insertUser(userEntity: UserEntity)
    fun getUser(): UserEntity
}