package ai.kaira.domain.assessment.model

class AssessmentAnswer(var id: Int,
                       var title: String,
                       var value: Int,
                       var duration: Double,
                       var time: Double,
                       var selected:Boolean){

    fun clone():AssessmentAnswer{
        return AssessmentAnswer(id, title, value, duration, time,selected)
    }
}