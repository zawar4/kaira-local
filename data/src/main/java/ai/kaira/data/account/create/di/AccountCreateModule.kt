package ai.kaira.data.account.create.di

import ai.kaira.data.account.create.datasource.network.AccountCreateNetworkDataSource
import ai.kaira.data.account.create.datasource.network.AccountCreateNetworkDataSourceImp
import ai.kaira.data.account.create.repository.AccountCreateRepositoryImp
import ai.kaira.domain.account.create.repository.AccountCreateRepository
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