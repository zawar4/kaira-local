package ai.kaira.data.assessment.model

import ai.kaira.domain.assessment.model.Strategy
import ai.kaira.domain.assessment.model.StrategySentence
import ai.kaira.domain.assessment.model.Stress
import com.google.gson.annotations.SerializedName

data class StrategyResponse(val strategy: StrategySentence, val stress: Stress){

    fun toStrategy():Strategy{
        return Strategy(strategy, stress)
    }
}
