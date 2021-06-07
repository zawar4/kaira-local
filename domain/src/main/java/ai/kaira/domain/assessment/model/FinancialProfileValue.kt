package ai.kaira.domain.assessment.model

import androidx.annotation.Keep
@Keep
data class FinancialProfileValue(
        var type: FinancialProfileType,
        var average: Double) {}