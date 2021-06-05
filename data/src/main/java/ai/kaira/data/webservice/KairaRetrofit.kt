package ai.kaira.data.webservice

import ai.kaira.data.utils.APIConfig
import androidx.annotation.Keep
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Keep
class KairaRetrofit {

    companion object {
        lateinit var retrofit: Retrofit
        private fun createKairaRouter(): KairaApiRouter {
            val baseUrl = APIConfig.getBaseUrl()
            val version = APIConfig.getAPIVersion()
            val url = "$baseUrl$version/"
            var interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().readTimeout(5, TimeUnit.MINUTES).connectTimeout(5, TimeUnit.MINUTES).addInterceptor(interceptor).addInterceptor(AuthorizationInterceptor()).build()
            retrofit = Retrofit.Builder().client(client)
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl(url)
                .build()

            return retrofit.create(KairaApiRouter::class.java)
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

        fun getKairaRouter() : KairaApiRouter {
            return createKairaRouter()
        }

    }
}