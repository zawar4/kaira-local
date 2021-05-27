package ai.kaira.data.banking.institution.datasource.local

import ai.kaira.domain.banking.institution.model.Institution

interface InstitutionLocalDataSource {

    fun getAllInstitutions(locale:String):ArrayList<Institution>
}