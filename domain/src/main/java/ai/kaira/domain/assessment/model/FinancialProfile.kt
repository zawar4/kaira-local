package ai.kaira.domain.assessment.model

import androidx.annotation.Keep
@Keep
data class FinancialProfile(
        var average: Double,
        var types: List<FinancialProfileValue>,
        var answers: List<ProfileQuestionAnswer>) {}