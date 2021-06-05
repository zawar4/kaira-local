package ai.kaira.domain.assessment.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
enum class PsychologicalProfileType (val rawValue: Int) {
    @SerializedName("0")
    SOCIAL_STATUS(0),
    @SerializedName("1")
    AUTONOMY(1),
    @SerializedName("2")
    BENEVOLENCE(2),
    @SerializedName("3")
    HEDONISM(3);

    companion object {
        operator fun invoke(rawValue: Int) = PsychologicalProfileType.values().firstOrNull { it.rawValue == rawValue }
    }
}