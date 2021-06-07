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

@Keep
class AIRetrofit {
    companion object {
        lateinit var retrofit: Retrofit

        private fun createAIRouter():AIApiRouter{
            val baseUrl = APIConfig.getAIBaseUrl()
            var interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            retrofit = Retrofit.Builder().client(client)
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
            return retrofit.create(AIApiRouter::class.java)
        }

        fun getAIRouter(): AIApiRouter {
            return createAIRouter()
        }

    }
}