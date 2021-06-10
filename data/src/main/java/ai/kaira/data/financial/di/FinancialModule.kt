package ai.kaira.data.financial.di

import ai.kaira.data.financial.datasource.network.FinancialNetworkDataSource
import ai.kaira.data.financial.datasource.network.FinancialNetworkDataSourceImp
import ai.kaira.data.financial.repository.FinancialRepositoryImp
import ai.kaira.domain.financial.repository.FinancialRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent


@Module
@InstallIn(ActivityComponent::class)
abstract class FinancialModule {

    @Binds
    abstract fun bindFinancialNetworkDatasource(financialNetworkDataSourceImp: FinancialNetworkDataSourceImp) : FinancialNetworkDataSource
    @Binds
    abstract fun bindFinancialRepository(financialRepositoryImp: FinancialRepositoryImp) : FinancialRepository
}