package ai.kaira.domain.assessment.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.assessment.model.AssessmentAnswer
import ai.kaira.domain.assessment.model.AssessmentAnswerClick
import ai.kaira.domain.assessment.respository.AssessmentRepository
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class AssessmentQuestionAnswered @Inject constructor(private val assessmentRepository: AssessmentRepository, viewModelCoroutineScope: CoroutineScope) : BaseUseCase(viewModelCoroutineScope) {


    fun onAssessmentQuestionAnswered(screenVisibleTime :Double,assessmentAnswerClick: AssessmentAnswerClick,currentAssessmentAnswer: AssessmentAnswer?,newAssessmentAnswer: AssessmentAnswer): AssessmentAnswer {
        if (currentAssessmentAnswer == null) {
            screenVisibleTime.let {
                val duration: Double = (assessmentAnswerClick.time - it) / 1000.00
                Log.v("DURATION", duration.toString())
                return AssessmentAnswer(newAssessmentAnswer.id, newAssessmentAnswer.title, newAssessmentAnswer.value, duration, assessmentAnswerClick.time,true)
            }
        } else {
            val duration = (assessmentAnswerClick.time - currentAssessmentAnswer.time) / 1000.00
            Log.v("DURATION", duration.toString())
            return AssessmentAnswer(newAssessmentAnswer.id, newAssessmentAnswer.title, newAssessmentAnswer.value, duration, assessmentAnswerClick.time,true)
        }
    }

    fun isQuestionAlreadyAnswered(assessmentId:Int,assessmentType:Int,questionId:Int):Int{
        return assessmentRepository.isQuestionAlreadyAnswered(assessmentId,assessmentType,questionId)
    }

    fun saveSelectedAssessmentAnswer(assessmentId:Int,assessmentType:Int,questionId:Int,assessmentAnswerPosition:Int) {
        assessmentRepository.saveSelectedAssessmentAnswer(assessmentId,assessmentType,questionId,assessmentAnswerPosition)
    }
}