package ai.kaira.data.introduction.datasource.database.source

import ai.kaira.data.introduction.datasource.database.entity.UserEntity

interface IntroductionLocalDataSource {
    fun insertUser(userEntity: UserEntity)
}