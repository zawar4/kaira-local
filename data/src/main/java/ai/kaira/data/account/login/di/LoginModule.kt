package ai.kaira.data.account.login.di

import ai.kaira.data.account.login.datasource.local.LoginLocalDataSource
import ai.kaira.data.account.login.datasource.local.LoginLocalDataSourceImp
import ai.kaira.data.account.login.datasource.network.LoginNetworkDataSource
import ai.kaira.data.account.login.datasource.network.LoginNetworkDataSourceImp
import ai.kaira.data.account.login.repository.LoginRepositoryImp
import ai.kaira.domain.account.login.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
abstract class LoginModule {

    @Binds
    abstract fun bindLoginNetworkDataSource(loginNetworkDataSourceImp: LoginNetworkDataSourceImp) : LoginNetworkDataSource

    @Binds
    abstract fun bindLoginRepository(loginRepositoryImp: LoginRepositoryImp):LoginRepository

    @Binds
    abstract fun bindLoginLocalDataDataSource(loginLocalDataSourceImp: LoginLocalDataSourceImp): LoginLocalDataSource
}