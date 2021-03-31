package ai.kaira.domain.assessment.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.assessment.model.Strategy
import ai.kaira.domain.assessment.respository.AssessmentRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class SaveStrategy @Inject constructor(private val assessmentRepository: AssessmentRepository, viewModelCoroutineScope: CoroutineScope) : BaseUseCase(viewModelCoroutineScope) {

    operator fun invoke(strategy: Strategy){
        saveStrategy(strategy)
    }

    private fun saveStrategy(strategy: Strategy){
        assessmentRepository.saveStrategy(strategy)
    }

}