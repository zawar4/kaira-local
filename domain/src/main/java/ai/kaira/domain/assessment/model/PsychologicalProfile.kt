package ai.kaira.domain.assessment.model

import androidx.annotation.Keep

@Keep
data class PsychologicalProfile(
        var type: PsychologicalProfileType,
        var answers: List<ProfileQuestionAnswer>,
        var stress: PsychologicalStress
) {}