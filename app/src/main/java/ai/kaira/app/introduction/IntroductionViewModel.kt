package ai.kaira.app.introduction


import ai.kaira.app.application.BaseViewModel
import ai.kaira.app.utils.Extensions.Companion.isConnectedToInternet
import ai.kaira.domain.KairaResult
import ai.kaira.domain.ResultState
import ai.kaira.domain.assessment.model.AssessmentType
import ai.kaira.domain.assessment.model.Strategy
import ai.kaira.domain.introduction.model.User
import ai.kaira.domain.introduction.usecase.IntroductionUsecase
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

class IntroductionViewModel(private val introductionUsecase: IntroductionUsecase) : BaseViewModel() {

    var userResultLiveData = MediatorLiveData<User>()
    var reduceAvatarHeightLiveData : MutableLiveData<Boolean> = MutableLiveData()
    var isAvatarReduced : Boolean = false
    var displayedAssessmentFields : Boolean = false
    var displayIntroductionFields : Boolean = true
    var displayAssessmentFieldsLiveData : MutableLiveData<User> = MutableLiveData()
    var displayIntroductionFieldsLiveData : MutableLiveData<Boolean> = MutableLiveData()
    var hideAssessmentFieldsLiveData : MutableLiveData<Unit> = MutableLiveData()
    var processAssessmentProfilesLiveData = MediatorLiveData<Strategy>()

    lateinit var user : User

    fun reduceAvatarHeight(change: Boolean){
        isAvatarReduced = change
        reduceAvatarHeightLiveData.value = change
    }

    fun isAvatarHeightReduced() :Boolean{
        return isAvatarReduced
    }
    fun onAvatarHeightChange():MutableLiveData<Boolean>{
        return reduceAvatarHeightLiveData
    }

    fun onHideAssessmentFields():MutableLiveData<Unit>{
        return hideAssessmentFieldsLiveData
    }

    fun hideAssessmentFields(){
        hideAssessmentFieldsLiveData.value = Unit
    }

    fun onDisplayAssessmentFields():MutableLiveData<User>{
        return displayAssessmentFieldsLiveData
    }
    fun isAssessmentFieldsDisplayed():Boolean{
        return displayedAssessmentFields
    }
    fun displayAssessmentFields(display:Boolean){
        displayedAssessmentFields = display
        if(!display){
            hideAssessmentFieldsLiveData.value = Unit
        }else{
            displayAssessmentFieldsLiveData.value = user
        }

    }

    fun displayAssessmentFields(display:Boolean,user:User){
        displayedAssessmentFields = display
        displayAssessmentFieldsLiveData.value = user
        this.user = user
    }

    fun onDisplayIntroductionFields():MutableLiveData<Boolean>{
        return displayIntroductionFieldsLiveData
    }

    fun displayIntroductionFields(display : Boolean){
        displayIntroductionFields = display
        displayIntroductionFieldsLiveData.value = display
    }

    fun createUser(firstName: String, languageLocale: String){
        var createUserLiveData = introductionUsecase.createUser(firstName, languageLocale)
        userResultLiveData.addSource(createUserLiveData) { t ->
            val result: KairaResult<User>? = t
            when (result?.status) {
                ResultState.SUCCESS -> {
                    result.data?.let { it ->
                        user = it
                        userResultLiveData.value = result.data
                        introductionUsecase.saveUser(it)
                        userResultLiveData.removeSource(createUserLiveData)
                    }

                }
                ResultState.ERROR -> {
                    result.message?.let{it->
                        showError(it)
                    }
                    userResultLiveData.removeSource(createUserLiveData)
                }
                ResultState.EXCEPTION -> {
                    result.message?.let{it->
                        showConnectivityError()
                    }
                    userResultLiveData.removeSource(createUserLiveData)
                }
            }
        }
    }


    fun processAssessmentProfiles(languageLocale: String){
        val liveDataSource = introductionUsecase.processAssessmentProfiles(languageLocale)
        processAssessmentProfilesLiveData.addSource(liveDataSource){ result ->
            when(result.status){
                ResultState.LOADING->{
                    showLoading(true)
                }
                ResultState.EXCEPTION->{
                    showLoading(false)
                    /*result.message?.let{it->
                        showError(it)
                    }*/
                    showConnectivityError()
                    processAssessmentProfilesLiveData.removeSource(liveDataSource)
                }
                ResultState.ERROR->{
                    showLoading(false)
                    result.message?.let{it->
                        showError(it)
                    }
                    processAssessmentProfilesLiveData.removeSource(liveDataSource)
                }
                ResultState.SUCCESS ->{
                    result.data?.let{
                        introductionUsecase.saveStrategy(it)
                        processAssessmentProfilesLiveData.value = it
                    }
                    showLoading(false)
                    processAssessmentProfilesLiveData.removeSource(liveDataSource)
                }
            }
        }

    }


    fun onAssessmentProfilesProcessed(): MediatorLiveData<Strategy> {
        return processAssessmentProfilesLiveData
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

    fun fetchUser():MutableLiveData<User?>{
        return introductionUsecase.fetchUserAsync()
    }

}