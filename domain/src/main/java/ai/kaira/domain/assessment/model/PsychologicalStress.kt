package ai.kaira.domain.assessment.model

data class PsychologicalStress(
    var value:Boolean,
    val answers: List<ProfileQuestionAnswer>)
