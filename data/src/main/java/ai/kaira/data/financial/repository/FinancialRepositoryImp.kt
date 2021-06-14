package ai.kaira.data.financial.repository

import ai.kaira.data.financial.datasource.network.FinancialNetworkDataSource
import ai.kaira.domain.KairaResult
import ai.kaira.domain.financial.model.MyFinancials
import ai.kaira.domain.financial.repository.FinancialRepository
import androidx.annotation.Keep
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Keep
class FinancialRepositoryImp @Inject constructor(private val financialNetworkDataSource: FinancialNetworkDataSource) : FinancialRepository {

    override fun myFinancials(): Flow<KairaResult<MyFinancials>> {
        return financialNetworkDataSource.myFinancials()
    }

}