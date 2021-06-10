package ai.kaira.domain.financial.repository

import ai.kaira.domain.KairaResult
import ai.kaira.domain.financial.model.MyFinancials
import androidx.lifecycle.MutableLiveData

interface FinancialRepository {

    fun myFinancials() : MutableLiveData<KairaResult<MyFinancials>>
}