package ai.kaira.domain.assessment.model

data class PsychologicalProfile(
        var type: PsychologicalProfileType,
        var answers: List<ProfileQuestionAnswer>) {}