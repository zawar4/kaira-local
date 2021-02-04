package ai.kaira.data.introduction.repository

import ai.kaira.data.introduction.datasource.IntroductionNetworkDataSource
import ai.kaira.domain.Result
import ai.kaira.domain.ResultState
import ai.kaira.domain.introduction.model.User
import ai.kaira.domain.introduction.repository.IntroductionRepository
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class IntroductionRepositoryImp @Inject constructor(private val introductionNetworkDataSource: IntroductionNetworkDataSource) : IntroductionRepository {

    private val createUserLiveData: MutableLiveData<Result<User>> = MutableLiveData()
    override fun createUser(firstName: String, languageLocale: String): MutableLiveData<Result<User>> {
        introductionNetworkDataSource?.createUser(firstName,languageLocale).observeForever {
            when(it.resultState){
                ResultState.SUCCESS ->{
                    val user : ai.kaira.data.introduction.dto.User = it.data
                    val userModel = User(user.id,user.firstName,user.language,user.createdAt,user.verified,user.validGroupCode)
                    val result : Result<User> = Result(data = userModel,resultState = it.resultState)
                    createUserLiveData.value = result
                    // TODO SAVE USER
                }
                ResultState.LOADING->{
                    val result : Result<User> = Result(data = User(),resultState = it.resultState)
                    createUserLiveData.value = result
                }
                ResultState.ERROR->{
                    val result : Result<User> = Result(data = User(),resultState = it.resultState,error = it.error)
                    createUserLiveData.value = result
                }
            }
        }
        return createUserLiveData
    }

    override fun saveUser(user: User): MutableLiveData<Boolean> {
        TODO("Not yet implemented")
    }
}