package ai.kaira.app.introduction


import ai.kaira.app.BaseViewModel
import ai.kaira.domain.Result
import ai.kaira.domain.ResultState
import ai.kaira.domain.introduction.model.User
import ai.kaira.domain.introduction.usecase.IntroductionUsecase
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class IntroductionViewModel(private val introductionUsecase: IntroductionUsecase) : BaseViewModel() {

    var userLiveData = MediatorLiveData<User>()
    fun createUser(firstName: String, languageLocale: String) : MutableLiveData<User>{
        userLiveData.addSource(introductionUsecase.createUser(firstName, languageLocale)) { t ->
            val result: Result<User>? = t
            when (result?.resultState) {
                ResultState.SUCCESS -> {
                    showLoading(false)
                    userLiveData.value = result.data
                    introductionUsecase.saveUser(result.data)
                }
                ResultState.LOADING -> {
                    showLoading(true)
                }
                ResultState.ERROR -> {
                    showLoading(false)
                    showError(result.error)
                }
            }
        }
        return userLiveData
    }

}