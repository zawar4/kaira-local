package ai.kaira.domain.assessment.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.KairaResult
import ai.kaira.domain.assessment.model.FinancialProfile
import ai.kaira.domain.assessment.respository.AssessmentRepository
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class ComputeFinancialAssessmentProfile @Inject constructor(private val assessmentRepository: AssessmentRepository, viewModelCoroutineScope: CoroutineScope) : BaseUseCase(viewModelCoroutineScope) {

    operator fun invoke(assessmentType: Int,userId:String) : MutableLiveData<KairaResult<FinancialProfile>>{
        return computeFinancialAssessmentProfile(assessmentType,userId)
    }
     private fun computeFinancialAssessmentProfile(assessmentType: Int, userId:String) : MutableLiveData<KairaResult<FinancialProfile>> {
        return assessmentRepository.computeFinancialAssessmentProfile(assessmentType,userId)
    }
}