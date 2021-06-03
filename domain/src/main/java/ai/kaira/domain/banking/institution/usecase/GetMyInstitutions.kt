package ai.kaira.domain.banking.institution.usecase

import ai.kaira.domain.KairaResult
import ai.kaira.domain.banking.institution.model.Institution
import ai.kaira.domain.banking.institution.repository.InstitutionRepository
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class GetMyInstitutions @Inject constructor(private val institutionRepository: InstitutionRepository) {

    operator fun invoke():MutableLiveData<KairaResult<ArrayList<Institution>>>{
        return getMyInstitutions()
    }

    fun getMyInstitutions():MutableLiveData<KairaResult<ArrayList<Institution>>>{
        return institutionRepository.getMyInstitutions()
    }
}