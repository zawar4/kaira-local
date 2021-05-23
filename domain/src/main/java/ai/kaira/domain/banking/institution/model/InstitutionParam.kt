package ai.kaira.domain.banking.institution.model

import java.io.Serializable

data class InstitutionParam(val type:String, val username:String, val password:String) : Serializable
