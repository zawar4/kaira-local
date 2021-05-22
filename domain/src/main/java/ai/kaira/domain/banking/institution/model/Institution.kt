package ai.kaira.domain.banking.institution.model

import java.io.Serializable

data class Institution(val aggregator:Int,val type:String?, val group :String?,val name:String?, val main:Boolean?,val instructions:String?,val usernameInformations:UserInformations?,val passwordInformations: PasswordInformations?,val note:String?):Serializable
