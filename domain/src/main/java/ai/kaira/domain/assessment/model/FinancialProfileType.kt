package ai.kaira.domain.assessment.model

import com.google.gson.annotations.SerializedName

enum class FinancialProfileType (val rawValue: Int) {
    @SerializedName("0")
    SPENDING(0),
    @SerializedName("1")
    SAVING(1),
    @SerializedName("2")
    CREDIT(2),
    @SerializedName("3")
    PLANNING(3);

    companion object {
        operator fun invoke(rawValue: Int) = values().firstOrNull { it.rawValue == rawValue }
    }
}