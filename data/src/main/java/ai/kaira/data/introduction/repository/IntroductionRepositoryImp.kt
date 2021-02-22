package ai.kaira.data.introduction.repository

import ai.kaira.data.introduction.datasource.database.entity.UserEntity
import ai.kaira.data.introduction.datasource.database.source.IntroductionLocalDataSource
import ai.kaira.data.introduction.datasource.network.IntroductionNetworkDataSource
import ai.kaira.domain.Result
import ai.kaira.domain.introduction.model.User
import ai.kaira.domain.introduction.repository.IntroductionRepository
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject


class IntroductionRepositoryImp @Inject constructor(private val introductionNetworkDataSource: IntroductionNetworkDataSource, private val introductionLocalDataSource: IntroductionLocalDataSource) : IntroductionRepository {

    override fun createUser(firstName: String, languageLocale: String): MutableLiveData<Result<User>> {
        return introductionNetworkDataSource.createUser(firstName,languageLocale)
    }

    override fun saveUser(user: User) {
        introductionLocalDataSource.insertUser(UserEntity(user.id,user.firstName,user.language,user.createdAt,user.verified,user.validGroupCode))
    }

    override fun fetchUser(): User {
        val userEntity : UserEntity = introductionLocalDataSource.fetchUser()
        return User(userEntity.id,userEntity.firstName,userEntity.language,userEntity.createdAt,userEntity.verified,userEntity.validGroupCode)
    }

    override fun fetchUserAsync(): MutableLiveData<User?> {
        return  introductionLocalDataSource.fetchUserAsync()
    }
}