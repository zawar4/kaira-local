package ai.kaira.domain.assessment.model

import com.google.gson.annotations.SerializedName

data class StrategySentence(@SerializedName("proposed_strategy") val proposedStrategy:String,
                    @SerializedName("sentence") val sentence:String,
                    @SerializedName("sentence_reason") val sentenceReason:String,
                    @SerializedName("sentence2") val sentence2: String)
