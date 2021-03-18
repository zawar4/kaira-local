package ai.kaira.domain.assessment.model

import ai.kaira.domain.assessment.model.Strategy
import ai.kaira.domain.assessment.model.Stress
import com.google.gson.annotations.SerializedName

data class Strategy(@SerializedName("strategy") val strategy: Strategy, val stress: Stress)
