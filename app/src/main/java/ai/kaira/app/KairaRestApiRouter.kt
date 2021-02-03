package ai.kaira.app

import ai.kaira.app.utils.APIConfig
import ai.kaira.data.Introduction.dto.User
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface KairaRestApiRouter {


    @FormUrlEncoded
    @POST("users")
    fun createUser(@Field("firstName") firstName :String,@Field("language") language :String): Call<User>


    companion object {
        fun create(): KairaRestApiRouter {
            val baseUrl = APIConfig.getBaseUrl()
            val version = APIConfig.getAPIVersion()
            val url = "$baseUrl$version/"
            var interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(AuthorizationInterceptor()).build()
            val retrofit = Retrofit.Builder().client(client)
                .addConverterFactory(
                        GsonConverterFactory.create())
                .baseUrl(url)
                .build()

            return retrofit.create(KairaRestApiRouter::class.java)
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

        fun getRouter() : KairaRestApiRouter{
            return create()
        }
    }


}