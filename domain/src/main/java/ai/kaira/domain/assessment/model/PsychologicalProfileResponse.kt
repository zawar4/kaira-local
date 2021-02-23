package ai.kaira.domain.assessment.model

data class PsychologicalProfileResponse(
        var type: PsychologicalProfileType,
        var answers: List<ProfileQuestionAnswer>) {}