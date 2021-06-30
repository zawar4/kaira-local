package ai.kaira.domain.financial.model

import androidx.annotation.Keep

@Keep
data class Assets(val amount : Double, val items : ArrayList<AssetItem>){
}
