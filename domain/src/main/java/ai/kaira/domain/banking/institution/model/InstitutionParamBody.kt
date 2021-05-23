package ai.kaira.domain.banking.institution.model

import java.io.Serializable

data class InstitutionParamBody(val type:Int,val institution: InstitutionParam) : Serializable
