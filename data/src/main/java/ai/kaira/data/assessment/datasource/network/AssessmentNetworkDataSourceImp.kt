package ai.kaira.data.assessment.datasource.network

import ai.kaira.data.webservice.RestApiRouter
import ai.kaira.domain.Result
import ai.kaira.data.assessment.model.AssessmentAnswerRequestParam
import ai.kaira.domain.assessment.model.FinancialProfile
import ai.kaira.domain.assessment.model.PsychologicalProfile
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

    override fun computeFinancialAssessmentProfile(assessmentType: Int,userId:String): MutableLiveData<Result<FinancialProfile>> {
        val financialAssessmentProfileLiveData = MutableLiveData<Result<FinancialProfile>>()
        viewModelCoroutineScope.launch(IO) {
            val response = restApiRouter.computeFinancialAssessmentProfile(assessmentType,userId).execute()
            withContext(Main){
                if(response.isSuccessful){
                    val financialProfileResponse = response.body()
                    financialProfileResponse?.let {
                        financialAssessmentProfileLiveData.value = Result.success(data = financialProfileResponse.toFinancialProfile())
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

    override fun computePsychologicalAssessmentProfile(assessmentType: Int,userId:String): MutableLiveData<Result<PsychologicalProfile>> {
        val psychologicalAssessmentProfileLiveData = MutableLiveData<Result<PsychologicalProfile>>()
        viewModelCoroutineScope.launch(IO) {
            val response = restApiRouter.computePsychologicalAssessmentProfile(assessmentType,userId).execute()
            withContext(Main){
                if(response.isSuccessful){
                    val psychologicalProfileResponse = response.body()
                    psychologicalProfileResponse?.let {
                        psychologicalAssessmentProfileLiveData.value = Result.success(data = psychologicalProfileResponse.toPsychologicalProfile())
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