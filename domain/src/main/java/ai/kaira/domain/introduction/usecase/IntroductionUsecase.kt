package ai.kaira.domain.introduction.usecase

import ai.kaira.domain.Result
import ai.kaira.domain.assessment.model.Strategy
import ai.kaira.domain.assessment.usecase.AssessmentQuestionAnswered
import ai.kaira.domain.assessment.usecase.CompleteAssessment
import ai.kaira.domain.assessment.usecase.FetchUserProcessAssessmentProfiles
import ai.kaira.domain.assessment.usecase.SaveStrategy
import ai.kaira.domain.introduction.model.User
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject


class IntroductionUsecase @Inject constructor(val createUserUsecase: CreateUser,
                                              val fetchUserUsecase: FetchUser,
                                              val saveUserUsecase: SaveUser,
                                              val assessmentQuestionAnswered: AssessmentQuestionAnswered,
                                              val completeAssessment: CompleteAssessment,
                                              val fetchUserProcessAssessmentProfiles: FetchUserProcessAssessmentProfiles,
                                              val saveStrategy: SaveStrategy) {

    fun createUser(firstName:String, languageLocale: String) : MutableLiveData<Result<User>>{
        return createUserUsecase(firstName,languageLocale)
    }

    fun saveUser(user: User){
        saveUserUsecase(user)
    }

    fun fetchUser(): User{
        return fetchUserUsecase()
    }

    fun fetchUserAsync() : MutableLiveData<User?>{
        return fetchUserUsecase.fetchUserAsync()
    }

    fun deleteUserOldAssessmentsAnswers(){
        assessmentQuestionAnswered.deleteUserOldAssessmentsAnswers()
    }

    fun processAssessmentProfiles(languageLocale:String): MediatorLiveData<Result<Strategy>> {
        return fetchUserProcessAssessmentProfiles(languageLocale)
    }
    fun isAssessmentCompleted(assessmentType: Int):Boolean{
        return completeAssessment.isAssessmentCompleted(assessmentType)
    }
}