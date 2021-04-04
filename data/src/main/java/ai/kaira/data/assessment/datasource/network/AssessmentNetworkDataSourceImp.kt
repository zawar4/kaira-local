package ai.kaira.data.assessment.datasource.network

import ai.kaira.data.assessment.model.AssessmentAnswerRequestParam
import ai.kaira.data.assessment.model.ProcessAssessmentAnswersParam
import ai.kaira.data.webservice.AIApiRouter
import ai.kaira.data.webservice.KairaApiRouter
import ai.kaira.domain.KairaResult
import ai.kaira.domain.assessment.model.FinancialProfile
import ai.kaira.domain.assessment.model.PsychologicalProfile
import ai.kaira.domain.assessment.model.Strategy
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AssessmentNetworkDataSourceImp @Inject constructor(private val kairaApiRouter: KairaApiRouter, private val aiApiRouter: AIApiRouter, private val viewModelCoroutineScope: CoroutineScope) : AssessmentNetworkDataSource {
    override fun submitAssessmentAnswer(AssessmentAnswerRequestParam: AssessmentAnswerRequestParam): MutableLiveData<KairaResult<Unit>> {
        val submitAnswerLiveData = MutableLiveData<KairaResult<Unit>>()
        viewModelCoroutineScope.launch(IO) {
            try {
                val response = kairaApiRouter.submitAnswer(AssessmentAnswerRequestParam).execute()
                withContext(Main) {
                    if (response.isSuccessful) {
                        submitAnswerLiveData.value = KairaResult.success()
                    } else {
                        val error: String? = response.errorBody()?.string()
                        submitAnswerLiveData.value =
                            error?.let { it1 -> KairaResult.error(message = it1) }
                    }
                }
            }
            catch(exception:Exception){
                withContext(Main){
                    exception.message?.let{ message ->
                        submitAnswerLiveData.value = KairaResult.exception(message = message)
                    }
                }
                exception.printStackTrace()
            }
        }
        return submitAnswerLiveData
    }

    override fun computeFinancialAssessmentProfile(assessmentType: Int,userId:String): MutableLiveData<KairaResult<FinancialProfile>> {
        val financialAssessmentProfileLiveData = MutableLiveData<KairaResult<FinancialProfile>>()
        viewModelCoroutineScope.launch(IO) {
            try {
                val response =
                    kairaApiRouter.computeFinancialAssessmentProfile(assessmentType, userId)
                        .execute()
                withContext(Main) {
                    if (response.isSuccessful) {
                        val financialProfileResponse = response.body()
                        financialProfileResponse?.let {
                            financialAssessmentProfileLiveData.value =
                                KairaResult.success(data = financialProfileResponse.toFinancialProfile())
                        }
                    } else {
                        val error: String? = response.errorBody()?.string()
                        error?.let {
                            financialAssessmentProfileLiveData.value = KairaResult.error(message = it)
                        }

                    }
                }
            }
            catch(exception:Exception){
                withContext(Main) {
                    exception.message?.let { message ->
                        financialAssessmentProfileLiveData.value =
                            KairaResult.exception(message = message)
                    }
                }
                exception.printStackTrace()
            }
        }
        return financialAssessmentProfileLiveData
    }

    override fun computePsychologicalAssessmentProfile(assessmentType: Int,userId:String): MutableLiveData<KairaResult<PsychologicalProfile>> {
        val psychologicalAssessmentProfileLiveData = MutableLiveData<KairaResult<PsychologicalProfile>>()
        viewModelCoroutineScope.launch(IO) {
            try {
                val response =
                    kairaApiRouter.computePsychologicalAssessmentProfile(assessmentType, userId)
                        .execute()
                withContext(Main) {
                    if (response.isSuccessful) {
                        val psychologicalProfileResponse = response.body()
                        psychologicalProfileResponse?.let {
                            psychologicalAssessmentProfileLiveData.value =
                                KairaResult.success(data = psychologicalProfileResponse.toPsychologicalProfile())
                        }
                    } else {
                        val error: String? = response.errorBody()?.string()
                        error?.let {
                            psychologicalAssessmentProfileLiveData.value =
                                KairaResult.error(message = it)
                        }

                    }
                }
            }
            catch(exception:Exception){
                withContext(Main) {
                    exception.message?.let { message ->
                        psychologicalAssessmentProfileLiveData.value =
                            KairaResult.exception(message = message)
                    }
                }
                exception.printStackTrace()
            }
        }
        return psychologicalAssessmentProfileLiveData
    }

    override fun processAssessmentProfiles(processAssessmentAnswersParam: ProcessAssessmentAnswersParam,languageLocale: String): MutableLiveData<KairaResult<Strategy>> {
        val strategyAssessmentLiveData = MutableLiveData<KairaResult<Strategy>>()
        viewModelCoroutineScope.launch(IO) {
            withContext(Main){
                strategyAssessmentLiveData.value = KairaResult.loading()
            }
            try {
                val response = aiApiRouter.processAssessmentProfiles(
                    languageLocale,
                    processAssessmentAnswersParam
                ).execute()
                withContext(Main) {
                    if (response.isSuccessful) {
                        val strategyAssessmentResponse = response.body()
                        strategyAssessmentResponse?.let { it ->
                            strategyAssessmentLiveData.value = KairaResult.success(it.toStrategy())
                        }
                    } else {
                        val error: String? = response.errorBody()?.string()
                        error?.let {
                            strategyAssessmentLiveData.value = KairaResult.error(message = it)
                        }
                    }
                }
            }
            catch(exception:Exception){
                withContext(Main) {
                    exception.message?.let { message ->
                        strategyAssessmentLiveData.value = KairaResult.exception(message = message)
                    }
                }
                exception.printStackTrace()
            }
        }
        return strategyAssessmentLiveData
    }


}