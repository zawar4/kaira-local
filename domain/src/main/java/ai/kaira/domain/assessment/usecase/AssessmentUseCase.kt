package ai.kaira.domain.assessment.usecase

import ai.kaira.domain.KairaResult
import ai.kaira.domain.assessment.model.*
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class AssessmentUseCase @Inject constructor(private val fetchFinancialAssessmentUseCase: FetchFinancialAssessment,
                                            private val fetchPsychologicalAssessmentUseCase: FetchPsychologicalAssessment,
                                            private val assessmentQuestionAnsweredUseCase: AssessmentQuestionAnswered,
                                            private val completeAssessment: CompleteAssessment,
                                            private val fetchUserSubmitAssessmentAnswer: FetchUserSubmitAssessmentAnswer,
                                            private val fetchUserComputeAssessmentProfile: FetchUserComputeAssessmentProfile,
                                            private val saveAssessmentProfile: SaveAssessmentProfile,
                                            private val fetchStrategy: FetchStrategy) {

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

    fun markAssessmentAsComplete(assessmentType: Int,){
        completeAssessment.markAssessmentAsComplete(assessmentType)
    }

    fun computePsychologicalAssessmentProfile(assessmentType: Int): MediatorLiveData<KairaResult<PsychologicalProfile>> {
        return fetchUserComputeAssessmentProfile.computePsychologicalAssessmentProfile(assessmentType)
    }

    fun submitAssessmentAnswer(question: AssessmentQuestion, answer: AssessmentAnswer?, assessment: Assessment):MediatorLiveData<KairaResult<Unit>>{
        return fetchUserSubmitAssessmentAnswer(question,answer,assessment)
    }

    fun computeFinancialAssessmentProfile(assessmentType: Int): MediatorLiveData<KairaResult<FinancialProfile>> {
        return fetchUserComputeAssessmentProfile.computeFinancialAssessmentProfile(assessmentType)
    }

    fun savePsychologicalAssessmentProfile(psychologicalProfile: PsychologicalProfile) {
        saveAssessmentProfile.savePsychologicalAssessmentProfile(psychologicalProfile)
    }

    fun saveFinancialAssessmentProfile(financialProfile: FinancialProfile) {
        saveAssessmentProfile.saveFinancialAssessmentProfile(financialProfile)
    }

    fun fetchStrategy(): MutableLiveData<Strategy?> {
        return fetchStrategy.fetchStrategy()
    }
}