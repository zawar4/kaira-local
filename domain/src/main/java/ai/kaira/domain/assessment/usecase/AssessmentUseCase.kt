package ai.kaira.domain.assessment.usecase

import ai.kaira.domain.assessment.model.Assessment
import ai.kaira.domain.assessment.model.AssessmentAnswer
import ai.kaira.domain.assessment.model.AssessmentAnswerClick
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class AssessmentUseCase @Inject constructor(val fetchFinancialAssessmentUseCase: FetchFinancialAssessment, val fetchPsychologicalAssessmentUseCase: FetchPsychologicalAssessment, private val assessmentQuestionAnsweredUseCase: AssessmentQuestionAnswered) {
    fun fetchFinancialAssessment(locale:String): MutableLiveData<Assessment>{
        return fetchFinancialAssessmentUseCase(locale)
    }

    fun fetchPsychologicalAssessment(locale:String): MutableLiveData<Assessment>{
        return fetchPsychologicalAssessmentUseCase(locale)
    }

    fun onAssessmentQuestionAnswered(screenVisibleTime: Double, assessmentAnswerClick: AssessmentAnswerClick, currentAssessmentAnswer: AssessmentAnswer?, newAssessmentAnswer: AssessmentAnswer):AssessmentAnswer{
        return assessmentQuestionAnsweredUseCase.onAssessmentQuestionAnswered(screenVisibleTime,assessmentAnswerClick, currentAssessmentAnswer,newAssessmentAnswer)
    }
}