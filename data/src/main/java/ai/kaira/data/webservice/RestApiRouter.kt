package ai.kaira.data.webservice

import ai.kaira.data.utils.APIConfig
import ai.kaira.domain.Result
import ai.kaira.data.assessment.model.AssessmentAnswerRequestParam
import ai.kaira.data.introduction.model.UserResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface RestApiRouter {


    @FormUrlEncoded
    @POST("users")
    fun createUser(@Field("firstName") firstName: String, @Field("language") language: String): Call<UserResponse>


    @POST("assessments/answer")
    fun submitAnswer(@Body AssessmentAnswerRequestParam: AssessmentAnswerRequestParam) : Call<Result<Unit>>

    companion object {

        lateinit var retrofit: Retrofit
        private fun create(): RestApiRouter {
            val baseUrl = APIConfig.getBaseUrl()
            val version = APIConfig.getAPIVersion()
            val url = "$baseUrl$version/"
            var interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(AuthorizationInterceptor()).build()
            retrofit = Retrofit.Builder().client(client)
                .addConverterFactory(
                        GsonConverterFactory.create())
                .baseUrl(url)
                .build()

            return retrofit.create(RestApiRouter::class.java)
        }

        class AuthorizationInterceptor : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val apiKey = APIConfig.getAPIKey()
                val newRequest: Request = chain.request().newBuilder()
                        .addHeader("api-key", apiKey)
                        .build()
                return chain.proceed(newRequest)
            }
        }

        fun getRouter() : RestApiRouter {
            return create()
        }
    }
}