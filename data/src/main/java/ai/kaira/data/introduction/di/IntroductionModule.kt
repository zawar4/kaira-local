package ai.kaira.data.introduction.di

import ai.kaira.data.introduction.datasource.database.source.IntroductionLocalDataSource
import ai.kaira.data.introduction.datasource.database.source.IntroductionLocalDataSourceImp
import ai.kaira.data.introduction.datasource.network.IntroductionNetworkDataSource
import ai.kaira.data.introduction.datasource.network.IntroductionNetworkDataSourceImp
import ai.kaira.data.introduction.repository.IntroductionRepositoryImp
import ai.kaira.domain.introduction.repository.IntroductionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
abstract class IntroductionModule {

    @Binds
    abstract fun bindIntroductionRepository(introductionRepositoryImp: IntroductionRepositoryImp):IntroductionRepository

    @Binds
    abstract fun bindIntroductionNetworkDataSource(introductionNetworkDataSourceImp: IntroductionNetworkDataSourceImp): IntroductionNetworkDataSource

    @Binds
    abstract fun bindIntroductionLocalDataSource(introductionLocalDataSourceImp: IntroductionLocalDataSourceImp) : IntroductionLocalDataSource
}