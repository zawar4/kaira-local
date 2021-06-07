package ai.kaira.data.assessment.model

import ai.kaira.domain.assessment.model.Strategy
import ai.kaira.domain.assessment.model.StrategySentence
import ai.kaira.domain.assessment.model.Stress
import com.google.gson.annotations.SerializedName

import androidx.annotation.Keep
@Keep
data class StrategyResponse(val screen1:String, val screen2:String, @SerializedName("proposed_strategy") val proposedStrategy:String){

    fun toStrategy():Strategy{
        return Strategy(screen1, screen2,proposedStrategy)
    }
}
