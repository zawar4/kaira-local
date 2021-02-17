package ai.kaira.domain.assessment.model

class AssessmentQuestion (var result: AssessmentAnswer? = null,
                          var id: Int,
                          var type: Int,
                          var title: String,
                          var answers: List<AssessmentAnswer>) {
}