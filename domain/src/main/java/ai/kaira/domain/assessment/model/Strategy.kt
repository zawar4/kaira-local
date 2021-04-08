package ai.kaira.domain.assessment.model

import ai.kaira.domain.assessment.model.Strategy
import ai.kaira.domain.assessment.model.Stress
import com.google.gson.annotations.SerializedName

data class Strategy(val screen1:String, val screen2:String, @SerializedName("proposed_strategy") val proposedStrategy:String)
