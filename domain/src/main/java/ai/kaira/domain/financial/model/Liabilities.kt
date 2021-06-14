package ai.kaira.domain.financial.model

import androidx.annotation.Keep

@Keep
data class Liabilities(val amount : Double, val items : ArrayList<LiabilityItem>){

}
