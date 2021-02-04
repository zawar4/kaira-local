package ai.kaira.data.introduction.repository

import ai.kaira.data.introduction.datasource.IntroductionNetworkDataSource
import ai.kaira.domain.introduction.model.User
import ai.kaira.domain.introduction.repository.IntroductionRepository
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class IntroductionRepositoryImp @Inject constructor(private val introductionNetworkDataSource: IntroductionNetworkDataSource) : IntroductionRepository {

    val createUserLiveData: MutableLiveData<User> = MutableLiveData()
    override fun createUser(firstName: String, languageLocale: String): MutableLiveData<User> {
        introductionNetworkDataSource?.createUser(firstName,languageLocale).observeForever {
            createUserLiveData.value = User(it.id,it.firstName,it.language,it.createdAt,it.verified,it.validGroupCode)
            //SAVE USER
        }
        return createUserLiveData
    }

    override fun saveUser(user: User): MutableLiveData<Boolean> {
        TODO("Not yet implemented")
    }
}