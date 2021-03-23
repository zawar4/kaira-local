package ai.kaira.domain.assessment.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.assessment.model.FinancialProfile
import ai.kaira.domain.assessment.model.PsychologicalProfile
import ai.kaira.domain.assessment.respository.AssessmentRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class CompleteAssessment @Inject constructor(private val assessmentRepository: AssessmentRepository, viewModelCoroutineScope: CoroutineScope) : BaseUseCase(viewModelCoroutineScope) {

    fun markAssessmentAsComplete(assessmentType: Int){
        assessmentRepository.markAssessmentAsComplete(assessmentType)
    }

    fun isAssessmentCompleted(assessmentType: Int): Boolean{
        return assessmentRepository.isAssessmentCompleted(assessmentType)
    }
}