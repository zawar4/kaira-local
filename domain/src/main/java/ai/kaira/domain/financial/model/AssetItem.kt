package ai.kaira.domain.financial.model

import ai.kaira.domain.banking.institution.model.BankingAccountingType
import ai.kaira.domain.banking.institution.model.Institution
import androidx.annotation.Keep

@Keep
data class AssetItem(val id : String, val type : String, val balance : Double, val accountingType : BankingAccountingType, val title : String, val institution : Institution)
