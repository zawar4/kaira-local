package ai.kaira.domain.banking.institution.model

import java.io.Serializable

import androidx.annotation.Keep
@Keep
data class InstitutionParam(val type:String, val username:String, val password:String,val name : String) : Serializable
