package ai.kaira.data.account.create

import ai.kaira.domain.assessment.model.AssessmentType
import androidx.annotation.Keep

@Keep
enum class BankingAggregator(val value:Int) {
    wealthica(0),
    bi(1);

    companion object {
        operator fun invoke(type: Int) = BankingAggregator.values().firstOrNull { it.value == type }
        fun getCurrency (bankingAggregator: BankingAggregator):String{
            return when (bankingAggregator){
                wealthica -> "cad"
                bi -> "eur"
            }
        }
    }

}