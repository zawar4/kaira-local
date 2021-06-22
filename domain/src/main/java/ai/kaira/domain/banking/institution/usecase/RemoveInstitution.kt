package ai.kaira.domain.banking.institution.usecase

import ai.kaira.domain.KairaResult
import ai.kaira.domain.banking.institution.model.Institution
import ai.kaira.domain.banking.institution.model.SecurityAnswer
import ai.kaira.domain.banking.institution.repository.InstitutionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveInstitution @Inject constructor(private val institutionRepository: InstitutionRepository) {

    operator fun invoke(aggregator : Int, institutionId : String) : Flow<KairaResult<Void>> {
        return removeInstitution(aggregator, institutionId)
    }

    private fun removeInstitution(aggregator : Int, institutionId : String) : Flow<KairaResult<Void>> {
        return institutionRepository.removeInstitution(aggregator, institutionId)
    }
}