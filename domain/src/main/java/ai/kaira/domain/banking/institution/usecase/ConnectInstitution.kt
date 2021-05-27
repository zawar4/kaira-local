package ai.kaira.domain.banking.institution.usecase

import ai.kaira.domain.KairaResult
import ai.kaira.domain.banking.institution.model.ConnectedInstitution
import ai.kaira.domain.banking.institution.model.Institution
import ai.kaira.domain.banking.institution.model.InstitutionParamBody
import ai.kaira.domain.banking.institution.repository.InstitutionRepository
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class ConnectInstitution @Inject constructor(private val institutionRepository: InstitutionRepository) {
    operator fun invoke(institutionParamBody: InstitutionParamBody):MutableLiveData<KairaResult<ConnectedInstitution>>{
        return connectInstitution(institutionParamBody)
    }

    private fun connectInstitution(institutionParamBody: InstitutionParamBody):MutableLiveData<KairaResult<ConnectedInstitution>>{
        return institutionRepository.connectInstitution(institutionParamBody)
    }
}