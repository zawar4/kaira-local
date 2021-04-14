package ai.kaira.domain.assessment.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.KairaResult
import ai.kaira.domain.assessment.model.Assessment
import ai.kaira.domain.assessment.model.AssessmentAnswer
import ai.kaira.domain.assessment.model.AssessmentQuestion
import ai.kaira.domain.assessment.respository.AssessmentRepository
import ai.kaira.domain.introduction.model.User
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class SubmitAssessmentAnswer @Inject constructor(private val assessmentRepository: AssessmentRepository, viewModelCoroutineScope: CoroutineScope) : BaseUseCase(viewModelCoroutineScope) {

    operator fun invoke(userId:String,question: AssessmentQuestion, answer: AssessmentAnswer?, assessment: Assessment): MutableLiveData<KairaResult<Unit>> {
        return submitAssessmentAnswer(userId,question,answer,assessment)
    }

    private fun submitAssessmentAnswer(userId: String, question: AssessmentQuestion, answer: AssessmentAnswer?, assessment: Assessment): MutableLiveData<KairaResult<Unit>> {
        return assessmentRepository.submitAssessmentAnswer(userId,question,answer,assessment)
    }
}