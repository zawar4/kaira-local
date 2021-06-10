package ai.kaira.data.financial.datasource.network

import ai.kaira.data.webservice.KairaApiRouter
import ai.kaira.domain.KairaAction
import ai.kaira.domain.KairaResult
import ai.kaira.domain.financial.model.MyFinancials
import android.content.SharedPreferences
import androidx.annotation.Keep
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Keep
class FinancialNetworkDataSourceImp @Inject constructor(private val prefs: SharedPreferences, private val kairaApiRouter: KairaApiRouter, private val viewModelCoroutineScope: CoroutineScope) : FinancialNetworkDataSource {

    override fun myFinancials(): MutableLiveData<KairaResult<MyFinancials>> {
        val myFinancialsLiveData = MutableLiveData<KairaResult<MyFinancials>>()
        viewModelCoroutineScope.launch(IO) {

            withContext(Main){
                myFinancialsLiveData.value = KairaResult.loading()
            }

            try {
                val response = kairaApiRouter.getMyFinancials(prefs.getString("token","").toString()).execute()
                withContext(Main) {
                    if(response.isSuccessful) {
                        myFinancialsLiveData.value = KairaResult.success(data = response.body())
                    } else {
                        if(response.code() == 401) {
                            val error : String? = response.errorBody()?.string()
                            error?.let {
                                myFinancialsLiveData.value = KairaResult.error(message = error, kairaAction = KairaAction.UNAUTHORIZED_REDIRECT)
                            }
                        } else {
                            val error : String? = response.errorBody()?.string()
                            error?.let {
                                myFinancialsLiveData.value = KairaResult.error(message = error)
                            }
                        }
                    }
                }
            }
            catch(exception : Exception) {
                withContext(Main) {
                    exception.message?.let { message ->
                        myFinancialsLiveData.value = KairaResult.exception(message = message)
                    }
                }
                exception.printStackTrace()
            }

        }
        return myFinancialsLiveData
    }
}