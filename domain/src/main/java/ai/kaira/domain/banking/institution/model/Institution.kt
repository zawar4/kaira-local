package ai.kaira.domain.banking.institution.model

import java.io.Serializable

import androidx.annotation.Keep
@Keep
data class Institution(val aggregator:BankingAggregator,
                       val type:String?,
                       val group :String?,
                       val name:String?,
                       val main:Boolean?,
                       val instructions:String?,
                       val usernameInformations:UserInformations?,
                       val passwordInformations: PasswordInformations?,
                       val note:String?,
                       val id: String?,
                       val syncStatus: BankingInstitutionSyncStatus?,
                       val syncError: BankingInstitutionSyncError?):Serializable
{

    fun getLogoUrl():String{
        return "https://app.wealthica.com/images/institutions/$type.png"
    }
}
