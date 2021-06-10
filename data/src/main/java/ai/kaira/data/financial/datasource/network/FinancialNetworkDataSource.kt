package ai.kaira.data.financial.datasource.network

import ai.kaira.domain.KairaResult
import ai.kaira.domain.financial.model.MyFinancials
import androidx.annotation.Keep
import androidx.lifecycle.MutableLiveData

@Keep
interface FinancialNetworkDataSource {

    fun myFinancials() : MutableLiveData<KairaResult<MyFinancials>>
}