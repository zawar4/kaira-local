package ai.kaira.domain.assessment.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.assessment.model.PsychologicalProfile
import ai.kaira.domain.assessment.respository.AssessmentRepository
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class FetchPsychologicalAssessmentProfile @Inject constructor(private val assessmentRepository: AssessmentRepository, viewModelCoroutineScope: CoroutineScope) : BaseUseCase(viewModelCoroutineScope){


    fun fetchPsychologicalAssessmentProfileAsync(): MutableLiveData<PsychologicalProfile?> {
        return assessmentRepository.fetchPsychologicalAssessmentProfileAsync()
    }

    fun fetchPsychologicalAssessmentProfileSync(): PsychologicalProfile? {
        return assessmentRepository.fetchPsychologicalAssessmentProfileSync()
    }
}