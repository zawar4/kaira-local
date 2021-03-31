package ai.kaira.data.webservice

import ai.kaira.data.assessment.model.*
import ai.kaira.data.utils.APIConfig
import ai.kaira.domain.Result
import ai.kaira.domain.assessment.model.FinancialProfile
import ai.kaira.domain.assessment.model.PsychologicalProfile
import ai.kaira.data.introduction.model.UserResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface KairaApiRouter {

    @FormUrlEncoded
    @POST("users")
    fun createUser(@Field("firstName") firstName: String, @Field("language") language: String): Call<UserResponse>


    @GET("assessments/{assessment_type}/profile/{user_id}")
    fun computePsychologicalAssessmentProfile(@Path("assessment_type") assessmentType: Int,@Path("user_id")userId:String) : Call<PsychologicalProfileResponse>

    @GET("assessments/{assessment_type}/profile/{user_id}")
    fun computeFinancialAssessmentProfile(@Path("assessment_type") assessmentType: Int,@Path("user_id") userId:String) : Call<FinancialProfileResponse>

    @POST("assessments/answer")
    fun submitAnswer(@Body assessmentAnswerRequestParam: AssessmentAnswerRequestParam) : Call<Result<Unit>>

}