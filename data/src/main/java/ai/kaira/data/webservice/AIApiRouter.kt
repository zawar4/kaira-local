package ai.kaira.data.webservice

import ai.kaira.data.assessment.model.ProcessAssessmentAnswersParam
import ai.kaira.data.assessment.model.StrategyResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AIApiRouter {

    @POST("full_process?")
    fun processAssessmentProfiles(@Query("language") languageLocale: String, @Body processAssessmentAnswersParam: ProcessAssessmentAnswersParam) : Call<StrategyResponse>
}