package ai.kaira.data.assessment.datasource.network

import ai.kaira.domain.assessment.model.AnswerRequestParam
import ai.kaira.domain.Result
import ai.kaira.domain.assessment.model.AssessmentAnswer
import androidx.lifecycle.MutableLiveData

interface AssessmentNetworkDataSource {

    fun submitAnswer(answerRequestParam: AnswerRequestParam): MutableLiveData<Result<Unit>>
}