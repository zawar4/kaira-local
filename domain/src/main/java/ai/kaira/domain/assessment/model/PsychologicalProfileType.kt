package ai.kaira.domain.assessment.model

import com.google.gson.annotations.SerializedName

enum class PsychologicalProfileType (val rawValue: Int) {
    @SerializedName("0")
    SOCIAL_STATUS(0),
    @SerializedName("1")
    AUTONOMY(1),
    @SerializedName("2")
    BENEVOLENCE(2),
    @SerializedName("3")
    HEDONISM(3),
    @SerializedName("999")
    STRESS(999);

    companion object {
        operator fun invoke(rawValue: Int) = PsychologicalProfileType.values().firstOrNull { it.rawValue == rawValue }
    }
}