package ai.kaira.domain.assessment.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.assessment.model.Assessment
import ai.kaira.domain.assessment.respository.AssessmentRepository
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class FetchPsychologicalAssessment @Inject constructor(private val assessmentRepository: AssessmentRepository, viewModelCoroutineScope: CoroutineScope) : BaseUseCase(viewModelCoroutineScope) {
    operator fun invoke(locale:String): MutableLiveData<Assessment>{
        return fetchPsychologicalAssessment(locale)
    }


    private fun fetchPsychologicalAssessment(locale:String): MutableLiveData<Assessment> {
        return assessmentRepository.getPsychologicalAssessment(locale)
    }
}