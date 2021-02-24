package ai.kaira.domain.assessment.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.Result
import ai.kaira.domain.assessment.model.FinancialProfileResponse
import ai.kaira.domain.assessment.model.PsychologicalProfileResponse
import ai.kaira.domain.assessment.respository.AssessmentRepository
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class ComputePsychologicalAssessmentProfile @Inject constructor(private val assessmentRepository: AssessmentRepository, viewModelCoroutineScope: CoroutineScope) : BaseUseCase(viewModelCoroutineScope) {

    operator fun invoke(assessmentType: Int,userId:String) : MutableLiveData<Result<PsychologicalProfileResponse>>{
        return computePsychologicalAssessmentProfile(assessmentType,userId)
    }
    private fun computePsychologicalAssessmentProfile(assessmentType: Int, userId:String) : MutableLiveData<Result<PsychologicalProfileResponse>> {
        return assessmentRepository.computePsychologicalAssessmentProfile(assessmentType,userId)
    }
}