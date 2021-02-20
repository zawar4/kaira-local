package ai.kaira.domain.assessment

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.assessment.model.AnswerRequestParam
import ai.kaira.domain.assessment.model.Assessment
import ai.kaira.domain.assessment.model.AssessmentAnswer
import ai.kaira.domain.assessment.model.AssessmentQuestion
import ai.kaira.domain.assessment.respository.AssessmentRepository
import ai.kaira.domain.introduction.model.User
import ai.kaira.domain.introduction.repository.IntroductionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class GetUserSubmitAnswerUseCase @Inject constructor(private val introductionRepository: IntroductionRepository, private val assessmentRepository: AssessmentRepository, private val viewModelCoroutineScope: CoroutineScope) : BaseUseCase(viewModelCoroutineScope) {

    fun submitAnswer(question: AssessmentQuestion, answer: AssessmentAnswer?, assessment: Assessment){
        viewModelCoroutineScope.launch(IO) {
            val user : User? = introductionRepository.getUser()
            user?.let {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
                val timeCreatedDate = Date(answer!!.time)
                var answeredAt = dateFormat.format(timeCreatedDate)
                var answerRequestParam = AnswerRequestParam(user!!.id, assessment.id, assessment.version, assessment.type, question.id, question.type, answer!!.id, answer!!.value, answeredAt, answer.duration)
                assessmentRepository.submitAnswer(answerRequestParam)
            }

        }
    }
}