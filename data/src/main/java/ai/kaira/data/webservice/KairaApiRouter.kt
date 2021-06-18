package ai.kaira.data.webservice

import ai.kaira.domain.account.create.EmailBody
import ai.kaira.domain.account.create.TokenBody
import ai.kaira.domain.account.login.LoginBody
import ai.kaira.data.assessment.model.*
import ai.kaira.domain.banking.institution.model.InstitutionParamBody
import ai.kaira.domain.KairaResult
import ai.kaira.data.introduction.model.UserResponse
import ai.kaira.domain.account.create.model.Account
import ai.kaira.domain.account.login.ResetPasswordBody
import ai.kaira.domain.banking.institution.model.ConnectedInstitution
import ai.kaira.domain.banking.institution.model.Institution
import ai.kaira.domain.financial.model.MyFinancials
import ai.kaira.domain.introduction.model.User
import androidx.annotation.Keep
import retrofit2.Call
import retrofit2.http.*

@Keep
interface KairaApiRouter {


    @PUT("banking/{type}/institutions/{id}")
    fun submitSecurityAnswer(@Header("Authorization") authorization: String,@Path("type") type : Int,@Path("id") id : String) : Call<Institution>

    @GET("banking/dashboard")
    fun getMyFinancials(@Header("Authorization") authorization: String) : Call<MyFinancials>

    @GET("banking/institutions")
    fun getMyInstitutions(@Header("Authorization") authorization:String):Call<ArrayList<Institution>>

    @POST ("banking/institutions")
    fun connectInstitution(@Header("Authorization") authorization:String,@Body institutionParam: InstitutionParamBody) : Call<ConnectedInstitution>

    @POST("users/login")
    fun login(@Body loginBody: LoginBody): Call<UserResponse>

    @POST("users/password/forgot")
    fun forgotPassword(@Body emailBody: EmailBody) : Call<EmailBody>

    @POST("users/password/forgot")
    fun forgotPassword(@Body tokenBody: TokenBody) : Call<EmailBody>

    @POST("users/password/reset")
    fun resetPassword(@Body resetPasswordBody: ResetPasswordBody) : Call<Unit>
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


    @POST("users/verify")
    fun verifyAccount(@Body tokenBody: TokenBody):Call<Void>


    @POST("users/verify/new")
    fun sendVerificationEmail(@Body emailBody: EmailBody):Call<Void>

    @POST("users/verify/new")
    fun sendVerificationEmail(@Body tokenBody: TokenBody):Call<Void>
}