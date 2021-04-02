package ai.kaira.data.account.di

import ai.kaira.data.account.datasource.network.AccountCreateNetworkDataSource
import ai.kaira.data.account.datasource.network.AccountCreateNetworkDataSourceImp
import ai.kaira.data.account.repository.AccountCreateRepositoryImp
import ai.kaira.data.assessment.datasource.database.AssessmentLocalDataSource
import ai.kaira.data.assessment.datasource.database.AssessmentLocalDataSourceImp
import ai.kaira.data.assessment.datasource.network.AssessmentNetworkDataSource
import ai.kaira.data.assessment.datasource.network.AssessmentNetworkDataSourceImp
import ai.kaira.data.assessment.respository.AssessmentRepositoryImp
import ai.kaira.domain.account.repository.AccountCreateRepository
import ai.kaira.domain.assessment.respository.AssessmentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
abstract class AccountCreateModule {

    @Binds
    abstract fun bindAccountCreateRepository(accountCreateRepositoryImp: AccountCreateRepositoryImp): AccountCreateRepository

    @Binds
    abstract fun bindAccountCreateNetworkDataSource(accountCreateNetworkDataSourceImp: AccountCreateNetworkDataSourceImp): AccountCreateNetworkDataSource
}