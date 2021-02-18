package ai.kaira.domain.assessment.model

class Assessment(var id: Int,
                 var version: Int,
                 var type: Int,
                 var duration:Int,
                 var number:Int,
                 var title:String,
                 var description: String,
                 var mention: String? = null,
                 var questions: List<AssessmentQuestion>) {
}