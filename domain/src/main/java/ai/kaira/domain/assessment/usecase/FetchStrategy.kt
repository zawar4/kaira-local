package ai.kaira.domain.assessment.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.assessment.model.Strategy
import ai.kaira.domain.assessment.respository.AssessmentRepository
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class FetchStrategy @Inject constructor(private val assessmentRepository: AssessmentRepository, viewModelCoroutineScope: CoroutineScope) : BaseUseCase(viewModelCoroutineScope) {
    operator fun invoke():MutableLiveData<Strategy?>{
        return fetchStrategy()
    }

    fun fetchStrategy():MutableLiveData<Strategy?>{
        return assessmentRepository.fetchStrategy()
    }
}