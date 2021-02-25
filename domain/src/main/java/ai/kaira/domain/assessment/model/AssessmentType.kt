package ai.kaira.domain.assessment.model

import com.google.gson.annotations.SerializedName

enum class AssessmentType (val value: Int) {

    @SerializedName("1")
    PSYCHOLOGICAL(1),
    @SerializedName("2")
    FINANCIAL(2);

    companion object {
        operator fun invoke(type: Int) = values().firstOrNull { it.value == type }
    }
}