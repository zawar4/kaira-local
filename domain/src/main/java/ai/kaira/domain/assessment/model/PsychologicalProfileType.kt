package ai.kaira.domain.assessment.model

import com.google.gson.annotations.SerializedName

enum class PsychologicalProfileType (val rawValue: Int) {
    @SerializedName("0")
    socialStatus(0),
    @SerializedName("1")
    autonomy(1),
    @SerializedName("2")
    benevolence(2),
    @SerializedName("3")
    hedonism(3),
    @SerializedName("999")
    stress(999);

    companion object {
        operator fun invoke(rawValue: Int) = PsychologicalProfileType.values().firstOrNull { it.rawValue == rawValue }
    }
}