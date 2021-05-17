package ai.kaira.data.banking.institution.repository

import ai.kaira.data.banking.institution.datasource.local.InstitutionLocalDataSource
import ai.kaira.domain.banking.institution.model.Institution
import ai.kaira.domain.banking.institution.repository.InstitutionRepository
import javax.inject.Inject

class InstitutionRepositoryImp @Inject constructor(private val institutionLocalDataSource: InstitutionLocalDataSource): InstitutionRepository {

    override fun getAllInstitutions(): ArrayList<Institution> {
        return institutionLocalDataSource.getAllInstitutions()
    }
}