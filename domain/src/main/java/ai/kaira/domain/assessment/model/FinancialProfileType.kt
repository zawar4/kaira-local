package ai.kaira.domain.assessment.model

enum class FinancialProfileType (val rawValue: Int) {
    spending(0), saving(1), credit(2), planning(3);

    companion object {
        operator fun invoke(rawValue: Int) = FinancialProfileType.values().firstOrNull { it.rawValue == rawValue }
    }
}