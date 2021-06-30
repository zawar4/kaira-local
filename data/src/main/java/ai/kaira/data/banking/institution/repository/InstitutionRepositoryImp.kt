package ai.kaira.data.banking.institution.repository

import ai.kaira.data.banking.institution.datasource.local.InstitutionLocalDataSource
import ai.kaira.data.banking.institution.datasource.network.InstitutionNetworkDataSource
import ai.kaira.domain.KairaResult
import ai.kaira.domain.banking.institution.model.ConnectedInstitution
import ai.kaira.domain.banking.institution.model.Institution
import ai.kaira.domain.banking.institution.model.InstitutionParamBody
import ai.kaira.domain.banking.institution.model.SecurityAnswer
import ai.kaira.domain.banking.institution.repository.InstitutionRepository
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InstitutionRepositoryImp @Inject constructor(private val institutionLocalDataSource: InstitutionLocalDataSource, private val institutionNetworkDataSource: InstitutionNetworkDataSource): InstitutionRepository {

    override fun getAllInstitutions(locale:String): ArrayList<Institution> {
        return institutionLocalDataSource.getAllInstitutions(locale)
    }

    override fun connectInstitution(institutionParamBody: InstitutionParamBody): MutableLiveData<KairaResult<ConnectedInstitution>> {
        return institutionNetworkDataSource.connectInstitution(institutionParamBody)
    }

    override fun getMyInstitutions(): Flow<KairaResult<ArrayList<Institution>>> {
        return institutionNetworkDataSource.getMyInstitutions()
    }

    override fun verifyInstitutionCode(
        aggregator: Int,
        securityAnswer: SecurityAnswer,
        institutionId: String
    ): Flow<KairaResult<Institution>> {
        return institutionNetworkDataSource.verifyInstitutionCode(aggregator,securityAnswer,institutionId)
    }

    override fun removeInstitution(
        aggregator: Int,
        institutionId: String
    ): Flow<KairaResult<Void>> {
        return institutionNetworkDataSource.removeInstitution(aggregator,institutionId)
    }
}