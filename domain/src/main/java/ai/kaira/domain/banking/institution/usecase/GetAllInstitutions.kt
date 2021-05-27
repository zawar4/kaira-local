package ai.kaira.domain.banking.institution.usecase

import ai.kaira.domain.banking.institution.model.Institution
import ai.kaira.domain.banking.institution.repository.InstitutionRepository
import javax.inject.Inject

class GetAllInstitutions @Inject constructor(private val institutionRepository: InstitutionRepository) {

    operator fun invoke(locale:String):ArrayList<Institution>{
        return getAllInstitutions(locale)
    }

    fun getAllInstitutions(locale:String):ArrayList<Institution>{
        return institutionRepository.getAllInstitutions(locale)
    }
}