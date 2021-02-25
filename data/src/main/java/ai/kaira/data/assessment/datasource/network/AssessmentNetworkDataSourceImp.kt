package ai.kaira.data.assessment.datasource.network

import ai.kaira.data.webservice.RestApiRouter
import ai.kaira.domain.Result
import ai.kaira.domain.ResultState
import ai.kaira.data.assessment.model.AssessmentAnswerRequestParam
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AssessmentNetworkDataSourceImp @Inject constructor(val restApiRouter: RestApiRouter, private val viewModelCoroutineScope: CoroutineScope) : AssessmentNetworkDataSource {
    override fun submitAssessmentAnswer(AssessmentAnswerRequestParam: AssessmentAnswerRequestParam): MutableLiveData<Result<Unit>> {
        val submitAnswerLiveData = MutableLiveData<Result<Unit>>()
        viewModelCoroutineScope.launch(IO) {
            val response = restApiRouter.submitAnswer(AssessmentAnswerRequestParam).execute()
            withContext(Main){
                if (response.isSuccessful){
                    submitAnswerLiveData.value = Result(resultState = ResultState.SUCCESS, data = Unit)
                }else{
                    val error : String? = response.errorBody()?.string()
                    submitAnswerLiveData.value = error?.let { it1 -> Result(error = it1, resultState = ResultState.ERROR, data = Unit) }
                }
            }
        }
        return submitAnswerLiveData
    }

}