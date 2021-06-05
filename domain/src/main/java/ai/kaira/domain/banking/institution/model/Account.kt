package ai.kaira.domain.banking.institution.model

import androidx.annotation.Keep

@Keep
data class Account(val id : String, val number : String, val balance : String, val amount : String, val currency : String, val type :String, val aggregator: String, val institution:String, val institutionId: String,val metadata:MetaData, val accountingType:Int)
