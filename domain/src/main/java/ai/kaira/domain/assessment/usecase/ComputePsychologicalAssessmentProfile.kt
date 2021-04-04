package ai.kaira.domain.assessment.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.KairaResult
import ai.kaira.domain.assessment.model.PsychologicalProfile
import ai.kaira.domain.assessment.respository.AssessmentRepository
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class ComputePsychologicalAssessmentProfile @Inject constructor(private val assessmentRepository: AssessmentRepository, viewModelCoroutineScope: CoroutineScope) : BaseUseCase(viewModelCoroutineScope) {

    operator fun invoke(assessmentType: Int,userId:String) : MutableLiveData<KairaResult<PsychologicalProfile>>{
        return computePsychologicalAssessmentProfile(assessmentType,userId)
    }
    private fun computePsychologicalAssessmentProfile(assessmentType: Int, userId:String) : MutableLiveData<KairaResult<PsychologicalProfile>> {
        return assessmentRepository.computePsychologicalAssessmentProfile(assessmentType,userId)
    }
}