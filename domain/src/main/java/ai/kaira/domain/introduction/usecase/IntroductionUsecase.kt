package ai.kaira.domain.introduction.usecase

import ai.kaira.domain.Result
import ai.kaira.domain.assessment.model.AssessmentType
import ai.kaira.domain.assessment.usecase.AssessmentQuestionAnswered
import ai.kaira.domain.assessment.usecase.CompleteAssessment
import ai.kaira.domain.introduction.model.User
import ai.kaira.domain.introduction.repository.IntroductionRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject


class IntroductionUsecase @Inject constructor(val createUserUsecase: CreateUser,val fetchUserUsecase: FetchUser, val saveUserUsecase: SaveUser,val assessmentQuestionAnswered: AssessmentQuestionAnswered,val completeAssessment: CompleteAssessment) {

    fun createUser(firstName:String, languageLocale: String) : MutableLiveData<Result<User>>{
        return createUserUsecase(firstName,languageLocale)
    }

    fun saveUser(user: User){
        saveUserUsecase(user)
    }

    fun fetchUser(): User{
        return fetchUserUsecase()
    }

    fun deleteUserOldAssessmentsAnswers(){
        assessmentQuestionAnswered.deleteUserOldAssessmentsAnswers()
    }

    fun isAssessmentCompleted(assessmentType: Int):Boolean{
        return completeAssessment.isAssessmentCompleted(assessmentType)
    }
}