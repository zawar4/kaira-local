package ai.kaira.domain.banking.institution.usecase

import ai.kaira.domain.KairaResult
import ai.kaira.domain.banking.institution.model.Institution
import ai.kaira.domain.banking.institution.model.SecurityAnswer
import ai.kaira.domain.banking.institution.repository.InstitutionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubmitAccountVerificationCode @Inject constructor(private val institutionRepository: InstitutionRepository) {

    operator fun invoke(aggregator : Int, securityAnswer: SecurityAnswer, institutionId : String) : Flow<KairaResult<Institution>> {
        return submitAccountVerificationCode(aggregator,securityAnswer, institutionId)
    }

    private fun submitAccountVerificationCode(aggregator : Int, securityAnswer: SecurityAnswer, institutionId : String) : Flow<KairaResult<Institution>> {
        return institutionRepository.verifyInstitutionCode(aggregator, securityAnswer, institutionId)
    }
}