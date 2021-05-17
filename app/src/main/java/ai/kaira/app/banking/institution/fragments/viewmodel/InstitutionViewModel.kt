package ai.kaira.app.banking.institution.fragments.viewmodel

import ai.kaira.app.application.BaseViewModel
import ai.kaira.domain.banking.institution.model.Institution
import ai.kaira.domain.banking.institution.usecase.InstitutionUseCase

class InstitutionViewModel(private val institutionUseCase: InstitutionUseCase) : BaseViewModel() {

    fun getAllInstitutions():ArrayList<Institution>{
        return institutionUseCase.getAllInstitutions()
    }
}