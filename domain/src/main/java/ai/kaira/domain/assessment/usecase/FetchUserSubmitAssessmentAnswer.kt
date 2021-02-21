package ai.kaira.domain.assessment.usecase

import ai.kaira.domain.BaseUseCase
import ai.kaira.domain.assessment.model.Assessment
import ai.kaira.domain.assessment.model.AssessmentAnswer
import ai.kaira.domain.assessment.model.AssessmentQuestion
import ai.kaira.domain.introduction.model.User
import ai.kaira.domain.introduction.usecase.FetchUser
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.*
import ai.kaira.domain.*
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchUserSubmitAssessmentAnswer @Inject constructor(private val fetchUser: FetchUser, private val submitAssessmentAnswer: SubmitAssessmentAnswer,private val viewModelCoroutineScope: CoroutineScope) : BaseUseCase(viewModelCoroutineScope) {

    operator fun invoke(question: AssessmentQuestion, answer: AssessmentAnswer?, assessment: Assessment):MutableLiveData<Result<Unit>>{
        return fetchUserSubmitAssessmentAnswer(question,answer,assessment)
    }
    private fun fetchUserSubmitAssessmentAnswer(question: AssessmentQuestion, answer: AssessmentAnswer?, assessment: Assessment):MutableLiveData<Result<Unit>>{
        val submitAssessmentAnswerLiveData = MutableLiveData<Result<Unit>>()
        viewModelCoroutineScope.launch(IO) {
            val user : User = fetchUser()
            withContext(Main){
                user.let {
                    val liveData = submitAssessmentAnswer(it,question,answer,assessment)
                    val mediator = MediatorLiveData<Result<Unit>>()
                    mediator.addSource(liveData){
                        submitAssessmentAnswerLiveData.value = it
                        mediator.removeSource(liveData)
                    }
                }
            }
        }
        return submitAssessmentAnswerLiveData;
    }
}