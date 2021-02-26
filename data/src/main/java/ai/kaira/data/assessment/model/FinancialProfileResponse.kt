package ai.kaira.data.assessment.model

import ai.kaira.domain.assessment.model.FinancialProfile
import ai.kaira.domain.assessment.model.FinancialProfileValue
import ai.kaira.domain.assessment.model.ProfileQuestionAnswer

data class FinancialProfileResponse(
        var average: Double,
        var types: List<FinancialProfileValue>,
        var answers: List<ProfileQuestionAnswer>) {

    fun toFinancialProfile():FinancialProfile{
        return FinancialProfile(average,types,answers)
    }
}