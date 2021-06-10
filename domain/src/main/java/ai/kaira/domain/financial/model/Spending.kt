package ai.kaira.domain.financial.model

import androidx.annotation.Keep

@Keep
data class Spending(val date : String, val amount : Double)
