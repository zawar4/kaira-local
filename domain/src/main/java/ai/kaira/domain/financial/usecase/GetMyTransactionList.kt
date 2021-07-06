package ai.kaira.domain.financial.usecase

import ai.kaira.domain.KairaResult
import ai.kaira.domain.financial.model.Transaction
import ai.kaira.domain.financial.repository.FinancialRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyTransactionList @Inject constructor(private val financialRepository: FinancialRepository ) {

    operator fun invoke(accountId : String) : Flow<KairaResult<ArrayList<Transaction>>> {
        return financialRepository.getMyTransactionList(accountId)
    }
}