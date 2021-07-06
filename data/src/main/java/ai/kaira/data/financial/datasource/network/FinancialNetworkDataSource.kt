package ai.kaira.data.financial.datasource.network

import ai.kaira.domain.KairaResult
import ai.kaira.domain.financial.model.MyFinancials
import ai.kaira.domain.financial.model.Transaction
import androidx.annotation.Keep
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow

@Keep
interface FinancialNetworkDataSource {

    fun getMyTransactionList(accountId : String) : Flow<KairaResult<ArrayList<Transaction>>>
    fun myFinancials() : Flow<KairaResult<MyFinancials>>
}