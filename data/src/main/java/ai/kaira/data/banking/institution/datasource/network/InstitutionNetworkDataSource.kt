package ai.kaira.data.banking.institution.datasource.network

import ai.kaira.domain.banking.institution.model.InstitutionParamBody
import ai.kaira.domain.KairaResult
import ai.kaira.domain.banking.institution.model.ConnectedInstitution
import ai.kaira.domain.banking.institution.model.Institution
import androidx.lifecycle.MutableLiveData

interface InstitutionNetworkDataSource {

    fun connectInstitution(institutionParamBody: InstitutionParamBody):MutableLiveData<KairaResult<ConnectedInstitution>>

    fun getMyInstitutions(): MutableLiveData<KairaResult<ArrayList<Institution>>>
}