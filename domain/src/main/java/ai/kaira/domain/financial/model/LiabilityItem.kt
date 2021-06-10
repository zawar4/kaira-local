package ai.kaira.domain.financial.model

import ai.kaira.domain.banking.institution.model.Institution

data class LiabilityItem(val id : String, val type : String, val balance : Double, val accountingType : Int, val title : String, val institution : Institution)
