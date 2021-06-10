package ai.kaira.domain.financial.usecase

import ai.kaira.domain.KairaResult
import ai.kaira.domain.financial.model.MyFinancials
import ai.kaira.domain.financial.repository.FinancialRepository
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class GetMyFinancials @Inject constructor(private val financialRepository: FinancialRepository) {

    operator fun invoke() : MutableLiveData<KairaResult<MyFinancials>>{
        return getMyFinancials()
    }

    fun getMyFinancials() : MutableLiveData<KairaResult<MyFinancials>>{
        return financialRepository.myFinancials()
    }

}