package ai.kaira.data.financial.datasource.network

import ai.kaira.domain.KairaResult
import ai.kaira.domain.financial.model.MyFinancials
import androidx.annotation.Keep
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow

@Keep
interface FinancialNetworkDataSource {

    fun myFinancials() : Flow<KairaResult<MyFinancials>>
}