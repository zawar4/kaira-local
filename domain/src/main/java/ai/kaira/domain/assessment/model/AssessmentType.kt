package ai.kaira.domain.assessment.model

enum class AssessmentType (val type: Int) {
    PSYCHOLOGICAL(1),
    FINANCIAL(2);

    companion object {
        operator fun invoke(type: Int) = values().firstOrNull { it.type == type }
    }
}