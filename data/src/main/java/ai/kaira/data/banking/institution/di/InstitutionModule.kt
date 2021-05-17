package ai.kaira.data.banking.institution.di

import ai.kaira.data.banking.institution.datasource.local.InstitutionLocalDataSource
import ai.kaira.data.banking.institution.datasource.local.InstitutionLocalDataSourceImp
import ai.kaira.data.banking.institution.repository.InstitutionRepositoryImp
import ai.kaira.domain.banking.institution.repository.InstitutionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
abstract class InstitutionModule {

    @Binds
    abstract fun bindInstitutionsLocalDataSource(institutionLocalDataSourceImp: InstitutionLocalDataSourceImp):InstitutionLocalDataSource

    @Binds
    abstract fun bindInstitutionsRepository(institutionRepositoryImp: InstitutionRepositoryImp):InstitutionRepository
}