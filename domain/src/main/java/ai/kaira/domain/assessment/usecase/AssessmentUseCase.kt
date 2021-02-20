package ai.kaira.domain.assessment.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.Result
import ai.kaira.domain.assessment.model.AnswerRequestParam
import ai.kaira.domain.assessment.model.Assessment
import ai.kaira.domain.assessment.model.AssessmentType
import ai.kaira.domain.assessment.respository.AssessmentRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class AssessmentUseCase @Inject constructor(private val assessmentRepository: AssessmentRepository, viewModelCoroutineScope: CoroutineScope) : BaseUseCase(viewModelCoroutineScope) {
    fun getFinancialAssessment(locale:String): MutableLiveData<Assessment>{
        return assessmentRepository.getFinancialAssessment(locale)
    }

    fun getPsychologicalAssessment(locale:String): MutableLiveData<Assessment>{
        return assessmentRepository.getPsychologicalAssessment(locale)
    }

    fun submitAnswer(answerRequestParam: AnswerRequestParam): LiveData<Result<Unit>>{
        return assessmentRepository.submitAnswer(answerRequestParam)
    }
}