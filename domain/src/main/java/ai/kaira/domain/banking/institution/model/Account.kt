package ai.kaira.domain.banking.institution.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Account(val id : String,
                   val number : String,
                   val balance : Double,
                   val amount : Double,
                   val currency : String?,
                   val type :BankAccountType,
                   val aggregator: String,
                   val institution:String,
                   val institutionId: String,
                   val metadata:MetaData,
                   val accountingType:BankingAccountingType) : Serializable {

    fun hideTransactions() : Boolean {
        return type == BankAccountType.investment || type == BankAccountType.retirement || type == BankAccountType.otherRegisteredAccount || type == BankAccountType.loan || type == BankAccountType.mortgage || id.contains("tfsa")
    }

}
