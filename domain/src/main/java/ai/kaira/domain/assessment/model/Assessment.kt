package ai.kaira.domain.assessment.model

import androidx.annotation.Keep

@Keep
data class Assessment(var id: Int,
                 var version: Int,
                 var type: AssessmentType,
                 var duration:Int,
                 var number:Int,
                 var title:String,
                 var description: String,
                 var mention: String? = null,
                 var questions: List<AssessmentQuestion>) {
}