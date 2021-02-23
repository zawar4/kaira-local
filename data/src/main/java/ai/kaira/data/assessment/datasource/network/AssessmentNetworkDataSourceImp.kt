package ai.kaira.data.assessment.datasource.network

import ai.kaira.data.webservice.RestApiRouter
import ai.kaira.domain.Result
import ai.kaira.data.assessment.model.AssessmentAnswerRequestParam
import ai.kaira.domain.assessment.model.FinancialProfileResponse
import ai.kaira.domain.assessment.model.PsychologicalProfileResponse
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
                    submitAnswerLiveData.value = Result.success()
                }else{
                    val error : String? = response.errorBody()?.string()
                    submitAnswerLiveData.value = error?.let { it1 -> Result.error(message = it1)
                    }
                }
            }
        }
        return submitAnswerLiveData
    }

    override fun fetchFinancialAssessmentProfile(assessmentType: Int,userId:String): MutableLiveData<Result<FinancialProfileResponse>> {
        val financialAssessmentProfileLiveData = MutableLiveData<Result<FinancialProfileResponse>>()
        viewModelCoroutineScope.launch(IO) {
            val response = restApiRouter.fetchFinancialAssessmentProfile(assessmentType,userId).execute()
            withContext(Main){
                if(response.isSuccessful){
                    val financialProfileResponse = response.body()
                    financialProfileResponse?.let {
                        financialAssessmentProfileLiveData.value = Result.success(data = financialProfileResponse)
                    }
                }else{
                    val error : String? = response.errorBody()?.string()
                    error?.let {
                        financialAssessmentProfileLiveData.value = Result.error(message = it)
                    }

                }
            }
        }
        return financialAssessmentProfileLiveData
    }

    override fun fetchPsychologicalAssessmentProfile(assessmentType: Int,userId:String): MutableLiveData<Result<PsychologicalProfileResponse>> {
        val psychologicalAssessmentProfileLiveData = MutableLiveData<Result<PsychologicalProfileResponse>>()
        viewModelCoroutineScope.launch(IO) {
            val response = restApiRouter.fetchPsychologicalAssessmentProfile(assessmentType,userId).execute()
            withContext(Main){
                if(response.isSuccessful){
                    val psychologicalProfileResponse = response.body()
                    psychologicalProfileResponse?.let {
                        psychologicalAssessmentProfileLiveData.value = Result.success(data = psychologicalProfileResponse)
                    }
                }else{
                    val error : String? = response.errorBody()?.string()
                    error?.let {
                        psychologicalAssessmentProfileLiveData.value = Result.error(message = it)
                    }

                }
            }
        }
        return psychologicalAssessmentProfileLiveData
    }

}