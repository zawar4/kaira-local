package ai.kaira.domain.assessment.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.KairaResult
import ai.kaira.domain.assessment.model.FinancialProfile
import ai.kaira.domain.assessment.model.PsychologicalProfile
import ai.kaira.domain.assessment.model.Strategy
import ai.kaira.domain.assessment.respository.AssessmentRepository
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class ProcessAssessmentProfiles @Inject constructor(private val assessmentRepository: AssessmentRepository, viewModelCoroutineScope: CoroutineScope) : BaseUseCase(viewModelCoroutineScope) {

    operator fun invoke(languageLocale:String,userId: String, financialAssessmentProfile: FinancialProfile, psychologicalAssessmentProfile: PsychologicalProfile): MutableLiveData<KairaResult<Strategy>> {
        return processAssessmentProfiles(languageLocale,userId,financialAssessmentProfile,psychologicalAssessmentProfile)
    }

    private fun processAssessmentProfiles(languageLocale:String,userId: String, financialAssessmentProfile: FinancialProfile, psychologicalAssessmentProfile: PsychologicalProfile): MutableLiveData<KairaResult<Strategy>>{
        return assessmentRepository.processAssessmentProfiles(languageLocale,userId,financialAssessmentProfile,psychologicalAssessmentProfile)
    }
}