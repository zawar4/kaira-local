package ai.kaira.data.banking.institution.repository

import ai.kaira.data.banking.institution.datasource.local.InstitutionLocalDataSource
import ai.kaira.data.banking.institution.datasource.network.InstitutionNetworkDataSource
import ai.kaira.domain.KairaResult
import ai.kaira.domain.banking.institution.model.ConnectedInstitution
import ai.kaira.domain.banking.institution.model.Institution
import ai.kaira.domain.banking.institution.model.InstitutionParamBody
import ai.kaira.domain.banking.institution.repository.InstitutionRepository
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class InstitutionRepositoryImp @Inject constructor(private val institutionLocalDataSource: InstitutionLocalDataSource, private val institutionNetworkDataSource: InstitutionNetworkDataSource): InstitutionRepository {

    override fun getAllInstitutions(): ArrayList<Institution> {
        return institutionLocalDataSource.getAllInstitutions()
    }

    override fun connectInstitution(institutionParamBody: InstitutionParamBody): MutableLiveData<KairaResult<ConnectedInstitution>> {
        return institutionNetworkDataSource.connectInstitution(institutionParamBody)
    }
}