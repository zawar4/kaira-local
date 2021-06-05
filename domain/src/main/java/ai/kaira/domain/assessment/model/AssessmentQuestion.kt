package ai.kaira.domain.assessment.model

import androidx.annotation.Keep

@Keep
data class AssessmentQuestion (var result: AssessmentAnswer? = null,
                          var id: Int,
                          var type: Int,
                          var title: String,
                          var answers: ArrayList<AssessmentAnswer>) {
}