package ai.kaira.data.account.create

import ai.kaira.domain.assessment.model.AssessmentType

enum class BankingAggregator(val value:Int) {
    wealthica(0),
    bi(1);

    companion object {
        operator fun invoke(type: Int) = BankingAggregator.values().firstOrNull { it.value == type }
    }
}