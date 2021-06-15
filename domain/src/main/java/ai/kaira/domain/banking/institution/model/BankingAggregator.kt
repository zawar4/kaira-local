package ai.kaira.domain.banking.institution.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
enum class BankingAggregator(val value:Int) {
    @SerializedName("0")
    wealthica(0),
    @SerializedName("1")
    bi(1);

    operator fun invoke(type: Int) = BankingAggregator.values().firstOrNull { it.value == type }
    companion object {
        fun getCurrency (bankingAggregator: BankingAggregator):String{
            return when (bankingAggregator){
                wealthica -> "CAD"
                bi -> "EUR"
            }
        }
    }

}