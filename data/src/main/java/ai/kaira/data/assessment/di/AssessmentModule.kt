package ai.kaira.data.assessment.di

import ai.kaira.data.assessment.datasource.database.AssessmentLocalDataSource
import ai.kaira.data.assessment.datasource.database.AssessmentLocalDataSourceImp
import ai.kaira.data.assessment.datasource.network.AssessmentNetworkDataSource
import ai.kaira.data.assessment.datasource.network.AssessmentNetworkDataSourceImp
import ai.kaira.data.assessment.respository.AssessmentRepositoryImp
import ai.kaira.domain.assessment.respository.AssessmentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
abstract class AssessmentModule {

    @Binds
    abstract fun bindAssessmentRepository(assessmentRepositoryImp: AssessmentRepositoryImp): AssessmentRepository

    @Binds
    abstract fun bindAssessmentLocalDataSource(assessmentLocalDataSourceImp: AssessmentLocalDataSourceImp): AssessmentLocalDataSource

    @Binds
    abstract fun bindAssessmentNetworkDataSource(assessmentNetworkDataSourceImp: AssessmentNetworkDataSourceImp): AssessmentNetworkDataSource

}