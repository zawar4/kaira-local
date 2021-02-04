package ai.kaira.app.introduction


import ai.kaira.domain.Result
import ai.kaira.domain.ResultState
import ai.kaira.domain.introduction.model.User
import ai.kaira.domain.introduction.usecase.CreateUserUsecase
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IntroductionViewModel(private val createUserUsecase: CreateUserUsecase) : ViewModel() {


    val onLoadLiveData : MutableLiveData<Boolean> = MutableLiveData()
    val onErrorLiveData : MutableLiveData<String> = MutableLiveData()
    val userLiveData : MutableLiveData<User> = MutableLiveData()
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
                    onErrorLiveData.value = result?.error
                }
            }
        }
        return userLiveData
    }
}