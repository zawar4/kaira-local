package ai.kaira.domain.assessment.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.assessment.model.FinancialProfile
import ai.kaira.domain.assessment.respository.AssessmentRepository
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class FetchFinancialAssessmentProfile @Inject constructor(private val assessmentRepository: AssessmentRepository, viewModelCoroutineScope: CoroutineScope) : BaseUseCase(viewModelCoroutineScope) {


    fun fetchFinancialAssessmentProfileAsync(): MutableLiveData<FinancialProfile?> {
        return assessmentRepository.fetchFinancialAssessmentProfileAsync()
    }

    fun fetchFinancialAssessmentProfileSync(): FinancialProfile? {
        return assessmentRepository.fetchFinancialAssessmentProfileSync()
    }
}