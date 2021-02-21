package ai.kaira.data.assessment.datasource.network

import ai.kaira.data.assessment.model.AssessmentAnswerRequestParam
import ai.kaira.domain.Result
import androidx.lifecycle.MutableLiveData

interface AssessmentNetworkDataSource {

    fun submitAssessmentAnswer(AssessmentAnswerRequestParam: AssessmentAnswerRequestParam): MutableLiveData<Result<Unit>>
}