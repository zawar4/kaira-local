package ai.kaira.domain.assessment.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.Result
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

    operator fun invoke(user:User,question: AssessmentQuestion, answer: AssessmentAnswer?, assessment: Assessment): MutableLiveData<Result<Unit>> {
        return submitAssessmentAnswer(user,question,answer,assessment)
    }

    private fun submitAssessmentAnswer(user: User, question: AssessmentQuestion, answer: AssessmentAnswer?, assessment: Assessment): MutableLiveData<Result<Unit>> {
        return assessmentRepository.submitAssessmentAnswer(user,question,answer,assessment)
    }
}