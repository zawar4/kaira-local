package ai.kaira.app.introduction


import ai.kaira.app.application.BaseViewModel
import ai.kaira.app.utils.Extensions.Companion.isConnectedToInternet
import ai.kaira.domain.Result
import ai.kaira.domain.ResultState
import ai.kaira.domain.assessment.model.AssessmentType
import ai.kaira.domain.introduction.model.User
import ai.kaira.domain.introduction.usecase.IntroductionUsecase
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

class IntroductionViewModel(private val introductionUsecase: IntroductionUsecase) : BaseViewModel() {

    var userResultLiveData = MediatorLiveData<User>()
    fun createUser(firstName: String, languageLocale: String){
        if(!isConnectedToInternet()){
            showConnectivityError()
        }else{
            var createUserLiveData = introductionUsecase.createUser(firstName, languageLocale)
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

    fun deleteUserOldAssessmentsAnswers(){
        introductionUsecase.deleteUserOldAssessmentsAnswers()
    }

    fun isAssessmentCompleted(assessmentType: Int):Boolean{
        return introductionUsecase.isAssessmentCompleted(assessmentType)
    }

}