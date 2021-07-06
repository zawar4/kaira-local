package ai.kaira.domain.financial.repository

import ai.kaira.domain.KairaResult
import ai.kaira.domain.financial.model.MyFinancials
import ai.kaira.domain.financial.model.Transaction
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow

interface FinancialRepository {

    fun myFinancials() : Flow<KairaResult<MyFinancials>>
    fun getMyTransactionList(accountId : String) : Flow<KairaResult<ArrayList<Transaction>>>
}