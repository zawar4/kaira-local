package ai.kaira.domain.banking.institution.repository

import ai.kaira.domain.KairaResult
import ai.kaira.domain.banking.institution.model.ConnectedInstitution
import ai.kaira.domain.banking.institution.model.Institution
import ai.kaira.domain.banking.institution.model.InstitutionParamBody
import androidx.lifecycle.MutableLiveData

interface InstitutionRepository {
    fun getAllInstitutions():ArrayList<Institution>
    fun connectInstitution(institutionParamBody: InstitutionParamBody) :MutableLiveData<KairaResult<ConnectedInstitution>>
}