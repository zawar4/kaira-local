package ai.kaira.domain.assessment.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.assessment.model.FinancialProfile
import ai.kaira.domain.assessment.model.PsychologicalProfile
import ai.kaira.domain.assessment.respository.AssessmentRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class SaveAssessmentProfile  @Inject constructor(private val assessmentRepository: AssessmentRepository, viewModelCoroutineScope: CoroutineScope) : BaseUseCase(viewModelCoroutineScope) {


    fun savePsychologicalAssessmentProfile(psychologicalProfile: PsychologicalProfile) {
        assessmentRepository.savePsychologicalAssessmentProfile(psychologicalProfile)
    }

    fun saveFinancialAssessmentProfile(financialProfile: FinancialProfile) {
        assessmentRepository.saveFinancialAssessmentProfile(financialProfile)
    }
}