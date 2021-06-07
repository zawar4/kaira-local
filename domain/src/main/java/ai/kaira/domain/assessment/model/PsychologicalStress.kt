package ai.kaira.domain.assessment.model

import androidx.annotation.Keep
@Keep
data class PsychologicalStress(
    var value:Boolean,
    val answers: List<ProfileQuestionAnswer>)
