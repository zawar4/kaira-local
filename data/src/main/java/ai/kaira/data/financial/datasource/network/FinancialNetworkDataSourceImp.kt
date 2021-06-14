package ai.kaira.data.financial.datasource.network

import ai.kaira.data.webservice.KairaApiRouter
import ai.kaira.domain.KairaAction
import ai.kaira.domain.KairaResult
import ai.kaira.domain.financial.model.MyFinancials
import android.content.SharedPreferences
import androidx.annotation.Keep
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Keep
class FinancialNetworkDataSourceImp @Inject constructor(private val prefs: SharedPreferences, private val kairaApiRouter: KairaApiRouter, private val viewModelCoroutineScope: CoroutineScope) : FinancialNetworkDataSource {

    override fun myFinancials(): Flow<KairaResult<MyFinancials>> = flow {
           emit(KairaResult.loading<MyFinancials>())
            try {
                val response = kairaApiRouter.getMyFinancials(prefs.getString("token","").toString()).execute()
                if(response.isSuccessful) {
                    emit(KairaResult.success(data = response.body()))
                } else {
                    if(response.code() == 401) {
                        val error : String? = response.errorBody()?.string()
                        error?.let {
                            emit(KairaResult.error<MyFinancials>(message = error, kairaAction = KairaAction.UNAUTHORIZED_REDIRECT))
                        }
                    } else {
                        val error : String? = response.errorBody()?.string()
                        error?.let {
                            emit(KairaResult.error<MyFinancials>(message = error))
                        }
                    }
                }
            }
            catch(exception : Exception) {
                exception.message?.let { message ->
                    emit(KairaResult.exception<MyFinancials>(message = message))
                exception.printStackTrace()
            }
        }
    }
}