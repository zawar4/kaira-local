package ai.kaira.domain.financial.repository

import ai.kaira.domain.KairaResult
import ai.kaira.domain.financial.model.MyFinancials
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow

interface FinancialRepository {

    fun myFinancials() : Flow<KairaResult<MyFinancials>>
}