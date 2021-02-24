package ai.kaira.domain.assessment.usecase

import ai.kaira.domain.Result
import ai.kaira.domain.assessment.model.*
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class AssessmentUseCase @Inject constructor(private val fetchFinancialAssessmentUseCase: FetchFinancialAssessment,
                                            private val fetchPsychologicalAssessmentUseCase: FetchPsychologicalAssessment,
                                            private val assessmentQuestionAnsweredUseCase: AssessmentQuestionAnswered,
                                            private val completeAssessment: CompleteAssessment,
                                            private val fetchUserSubmitAssessmentAnswer: FetchUserSubmitAssessmentAnswer,
                                            private val fetchUserComputeAssessmentProfile: FetchUserComputeAssessmentProfile) {
    fun fetchFinancialAssessment(locale:String): MutableLiveData<Assessment>{
        return fetchFinancialAssessmentUseCase(locale)
    }

    fun fetchPsychologicalAssessment(locale:String): MutableLiveData<Assessment>{
        return fetchPsychologicalAssessmentUseCase(locale)
    }

    fun onAssessmentQuestionAnswered(screenVisibleTime: Double, assessmentAnswerClick: AssessmentAnswerClick, currentAssessmentAnswer: AssessmentAnswer?, newAssessmentAnswer: AssessmentAnswer):AssessmentAnswer{
        return assessmentQuestionAnsweredUseCase.onAssessmentQuestionAnswered(screenVisibleTime,assessmentAnswerClick, currentAssessmentAnswer,newAssessmentAnswer)
    }

    fun saveSelectedAssessmentAnswer(assessmentId:Int,assessmentType:Int,questionId:Int,assessmentAnswerPosition:Int){
        assessmentQuestionAnsweredUseCase.saveSelectedAssessmentAnswer(assessmentId,assessmentType,questionId,assessmentAnswerPosition)
    }

    fun isQuestionAlreadyAnswered(assessmentId:Int,assessmentType:Int,questionId:Int):Int{
        return assessmentQuestionAnsweredUseCase.isQuestionAlreadyAnswered(assessmentId,assessmentType,questionId)
    }

    fun markAssessmentAsComplete(assessmentType: Int){
        completeAssessment.markAssessmentAsComplete(assessmentType)
    }

    fun computePsychologicalAssessmentProfile(assessmentType: Int): MediatorLiveData<Result<PsychologicalProfileResponse>> {
        return fetchUserComputeAssessmentProfile.computePsychologicalAssessmentProfile(assessmentType)
    }

    fun submitAssessmentAnswer(question: AssessmentQuestion, answer: AssessmentAnswer?, assessment: Assessment):MediatorLiveData<Result<Unit>>{
        return fetchUserSubmitAssessmentAnswer(question,answer,assessment)
    }

    fun computeFinancialAssessmentProfile(assessmentType: Int): MediatorLiveData<Result<FinancialProfileResponse>> {
        return fetchUserComputeAssessmentProfile.computeFinancialAssessmentProfile(assessmentType)
    }
}