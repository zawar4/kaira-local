package ai.kaira.domain.banking.institution.repository

import ai.kaira.domain.banking.institution.model.Institution
import androidx.lifecycle.MutableLiveData

interface InstitutionRepository {
    fun getAllInstitutions():ArrayList<Institution>
}