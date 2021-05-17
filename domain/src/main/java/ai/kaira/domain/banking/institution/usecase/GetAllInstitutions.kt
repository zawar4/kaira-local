package ai.kaira.domain.banking.institution.usecase

import ai.kaira.domain.banking.institution.model.Institution
import ai.kaira.domain.banking.institution.repository.InstitutionRepository
import javax.inject.Inject

class GetAllInstitutions @Inject constructor(private val institutionRepository: InstitutionRepository) {

    operator fun invoke():ArrayList<Institution>{
        return getAllInstitutions()
    }

    fun getAllInstitutions():ArrayList<Institution>{
        return institutionRepository.getAllInstitutions()
    }
}