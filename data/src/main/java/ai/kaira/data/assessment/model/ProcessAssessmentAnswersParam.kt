package ai.kaira.data.assessment.model

import ai.kaira.domain.assessment.model.FinancialProfile
import ai.kaira.domain.assessment.model.PsychologicalProfile

import androidx.annotation.Keep
@Keep
data class ProcessAssessmentAnswersParam(val userId: String,val financialProfile: FinancialProfile,val psychologicalProfile: PsychologicalProfile)
