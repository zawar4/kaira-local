package ai.kaira.domain.assessment.model

import com.google.gson.annotations.SerializedName

enum class FinancialProfileType (val rawValue: Int) {
    @SerializedName("0")
    spending(0),
    @SerializedName("1")
    saving(1),
    @SerializedName("2")
    credit(2),
    @SerializedName("3")
    planning(3);

    companion object {
        operator fun invoke(rawValue: Int) = values().firstOrNull { it.rawValue == rawValue }
    }
}