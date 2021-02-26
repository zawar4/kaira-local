package ai.kaira.domain.assessment.model

data class FinancialProfile(
        var average: Double,
        var types: List<FinancialProfileValue>,
        var answers: List<ProfileQuestionAnswer>) {}