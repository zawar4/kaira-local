package ai.kaira.domain.financial.model

import ai.kaira.domain.banking.institution.model.BankingAccountingType
import ai.kaira.domain.banking.institution.model.BankingAggregator
import androidx.annotation.Keep

@Keep
data class Transaction(val id : String,
                       val aggregator: BankingAggregator,
                       val accountId : String,
                       val institution : String,
                       val institutionId: String,
                       val category : BankingTransactionCategory,
                       val displayCategory : String,
                       val type : String,
                       val typeAggregator : String,
                       val name : String,
                       val amount : Double,
                       val currency : String,
                       val date : String,
                       val registeringDate : String,
                       val accountType: BankingAccountingType
)