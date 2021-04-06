package ai.kaira.data.webservice

import ai.kaira.data.account.EmailBody
import ai.kaira.data.assessment.model.*
import ai.kaira.data.utils.APIConfig
import ai.kaira.domain.KairaResult
import ai.kaira.domain.assessment.model.FinancialProfile
import ai.kaira.domain.assessment.model.PsychologicalProfile
import ai.kaira.data.introduction.model.UserResponse
import ai.kaira.domain.account.model.Account
import ai.kaira.domain.introduction.model.User
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
    fun submitAnswer(@Body assessmentAnswerRequestParam: AssessmentAnswerRequestParam) : Call<KairaResult<Unit>>

    @GET("groups/{group_code}")
    fun groupCodeExists(@Path("group_code")groupCode:String): Call<Unit>

    @GET("users/validate")
    fun emailExists(@Query("email") email:String): Call<Boolean>

    @POST("users/account")
    fun createAccount(@Body accountDetails:Account):Call<User>


    @POST
    fun verifyAccount(@Url url: String):Call<Void>


    @POST("users/verify/new")
    fun sendVerificationEmail(@Body emailBody: EmailBody):Call<Void>
}