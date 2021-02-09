package ai.kaira.app.introduction


import ai.kaira.app.BaseViewModel
import ai.kaira.domain.Result
import ai.kaira.domain.ResultState
import ai.kaira.domain.introduction.model.User
import ai.kaira.domain.introduction.usecase.IntroductionUsecase
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

class IntroductionViewModel(private val introductionUsecase: IntroductionUsecase) : BaseViewModel() {

    var userResultLiveData = MediatorLiveData<User>()
    var createUserLiveData  = MutableLiveData<Result<User>>()

    fun createUser(firstName: String, languageLocale: String){
        if(!isConnectedToInternet()){
            showConnectivityError()
        }else{
            createUserLiveData = introductionUsecase.createUser(firstName, languageLocale)
            userResultLiveData.addSource(createUserLiveData) { t ->
                val result: Result<User>? = t
                when (result?.resultState) {
                    ResultState.SUCCESS -> {
                        userResultLiveData.value = result.data
                        introductionUsecase.saveUser(result.data)
                        userResultLiveData.removeSource(createUserLiveData)
                    }
                    ResultState.ERROR -> {
                        showError(result.error)
                        userResultLiveData.removeSource(createUserLiveData)
                    }
                }
            }
        }
    }

    fun onCreateUser() : MediatorLiveData<User>{
        return userResultLiveData
    }



}