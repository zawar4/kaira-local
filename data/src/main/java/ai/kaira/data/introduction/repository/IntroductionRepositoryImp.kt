package ai.kaira.data.introduction.repository

import ai.kaira.data.introduction.datasource.database.entity.UserEntity
import ai.kaira.data.introduction.datasource.database.source.IntroductionLocalDataSource
import ai.kaira.data.introduction.datasource.network.IntroductionNetworkDataSource
import ai.kaira.domain.KairaResult
import ai.kaira.domain.introduction.model.User
import ai.kaira.domain.introduction.repository.IntroductionRepository
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject


class IntroductionRepositoryImp @Inject constructor(private val introductionNetworkDataSource: IntroductionNetworkDataSource, private val introductionLocalDataSource: IntroductionLocalDataSource) : IntroductionRepository {

    override fun createUser(firstName: String, languageLocale: String): MutableLiveData<KairaResult<User>> {
        return introductionNetworkDataSource.createUser(firstName,languageLocale)
    }

    override fun saveUser(user: User) {
        introductionLocalDataSource.insertUser(UserEntity(user.id,user.firstName,user.language,user.createdAt,user.verified,user.validGroupCode))
    }

    override fun fetchUser(): User? {
        val userEntity : UserEntity? = introductionLocalDataSource.fetchUser()
        userEntity?.let {
            return User(id =userEntity.id,firstName = userEntity.firstName, language = userEntity.language, createdAt = userEntity.createdAt, verified = userEntity.verified, validGroupCode = userEntity.validGroupCode)
        }?:run{
            return null
        }
    }

    override fun fetchUserAsync(): MutableLiveData<User?> {
        return introductionLocalDataSource.fetchUserAsync()
    }
}