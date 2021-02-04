package ai.kaira.app.introduction


import ai.kaira.domain.introduction.model.User
import ai.kaira.domain.introduction.usecase.CreateUserUsecase
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IntroductionViewModel(private val createUserUsecase: CreateUserUsecase) : ViewModel() {

    fun createUser(firstName:String, languageLocale: String) : MutableLiveData<User>{
        return createUserUsecase.createUser(firstName,languageLocale)
    }
}