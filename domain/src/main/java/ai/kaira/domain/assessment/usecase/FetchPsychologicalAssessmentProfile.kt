package ai.kaira.domain.assessment.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.assessment.model.PsychologicalProfile
import ai.kaira.domain.assessment.respository.AssessmentRepository
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class FetchPsychologicalAssessmentProfile @Inject constructor(private val assessmentRepository: AssessmentRepository, viewModelCoroutineScope: CoroutineScope) : BaseUseCase(viewModelCoroutineScope){

    operator fun invoke():MutableLiveData<PsychologicalProfile>{
        return fetchPsychologicalAssessmentProfile()
    }

    fun fetchPsychologicalAssessmentProfile():MutableLiveData<PsychologicalProfile>{
        return assessmentRepository.fetchPsychologicalAssessmentProfile()
    }
}