package ai.kaira.domain.financial.model

import androidx.annotation.Keep

@Keep
data class BalanceSheet(val amount : Double, val assets : Assets, val liabilities : Liabilities)
