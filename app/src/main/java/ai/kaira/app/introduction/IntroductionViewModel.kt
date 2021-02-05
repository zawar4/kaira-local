package ai.kaira.app.introduction


import ai.kaira.app.BaseViewModel
import ai.kaira.domain.Result
import ai.kaira.domain.ResultState
import ai.kaira.domain.introduction.model.User
import ai.kaira.domain.introduction.usecase.CreateUserUsecase
import androidx.lifecycle.MutableLiveData

class IntroductionViewModel(private val createUserUsecase: CreateUserUsecase) : BaseViewModel() {

    private val userLiveData : MutableLiveData<User> = MutableLiveData()

    fun createUser(firstName:String, languageLocale: String) : MutableLiveData<User>{
        createUserUsecase.createUser(firstName,languageLocale)?.observeForever {
            val result : Result<User>? = it
            when(result?.resultState){
                ResultState.SUCCESS -> {
                    onLoadLiveData.value = false
                    userLiveData.value = result.data
                }
                ResultState.LOADING -> {
                    onLoadLiveData.value = true
                }
                ResultState.ERROR -> {
                    onLoadLiveData.value = false
                    onErrorLiveData.value = result.error
                }
            }
        }
        return userLiveData
    }

}