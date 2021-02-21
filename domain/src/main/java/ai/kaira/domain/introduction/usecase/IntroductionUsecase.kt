package ai.kaira.domain.introduction.usecase

import ai.kaira.domain.Result
import ai.kaira.domain.introduction.model.User
import ai.kaira.domain.introduction.repository.IntroductionRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject


class IntroductionUsecase @Inject constructor(val createUserUsecase: CreateUser,val fetchUserUsecase: FetchUser, val saveUserUsecase: SaveUser) {

    fun createUser(firstName:String, languageLocale: String) : MutableLiveData<Result<User>>{
        return createUserUsecase(firstName,languageLocale)
    }

    fun saveUser(user: User){
        saveUserUsecase(user)
    }

    fun fetchUser(): User{
        return fetchUserUsecase()
    }
}