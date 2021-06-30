package ai.kaira.domain.banking.institution.model

import androidx.annotation.Keep
@Keep
data class ConnectedInstitution(val id : String, val type:String, val aggregator : BankingAggregator, val syncStatus:String, val accounts : ArrayList<Account>)
