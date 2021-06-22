package ai.kaira.domain.banking.institution.repository

import ai.kaira.domain.KairaResult
import ai.kaira.domain.banking.institution.model.ConnectedInstitution
import ai.kaira.domain.banking.institution.model.Institution
import ai.kaira.domain.banking.institution.model.InstitutionParamBody
import ai.kaira.domain.banking.institution.model.SecurityAnswer
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow

interface InstitutionRepository {
    fun getAllInstitutions(locale:String):ArrayList<Institution>
    fun connectInstitution(institutionParamBody: InstitutionParamBody) :MutableLiveData<KairaResult<ConnectedInstitution>>
    fun getMyInstitutions():Flow<KairaResult<ArrayList<Institution>>>
    fun verifyInstitutionCode(aggregator : Int, securityAnswer: SecurityAnswer, institutionId : String) : Flow<KairaResult<Institution>>
    fun removeInstitution(aggregator : Int, institutionId : String) : Flow<KairaResult<Void>>
}