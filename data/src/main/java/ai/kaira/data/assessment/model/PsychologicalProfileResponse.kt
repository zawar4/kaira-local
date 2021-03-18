package ai.kaira.data.assessment.model

import ai.kaira.domain.assessment.model.ProfileQuestionAnswer
import ai.kaira.domain.assessment.model.PsychologicalProfile
import ai.kaira.domain.assessment.model.PsychologicalProfileType
import ai.kaira.domain.assessment.model.PsychologicalStress

data class PsychologicalProfileResponse(
        var type: PsychologicalProfileType,
        var answers: List<ProfileQuestionAnswer>,
        var stress: PsychologicalStress) {

    fun toPsychologicalProfile():PsychologicalProfile{
        return PsychologicalProfile(type,answers,stress)
    }
}