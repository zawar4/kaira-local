package ai.kaira.domain.assessment.model

class AssessmentAnswer(var id: Int,
                       var title: String,
                       var value: Int,
                       var duration: Long,
                       var time: Long){

    fun clone():AssessmentAnswer{
        return AssessmentAnswer(id, title, value, duration, time)
    }
}