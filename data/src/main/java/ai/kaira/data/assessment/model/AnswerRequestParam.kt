package ai.kaira.data.assessment.model

data class AnswerRequestParam (val userId: Int, val assessmentId:Int,val assessmentVersion:Int, val assessmentType:Int,val questionId:Int, val questionType:Int,
val answerId:Int, val answerValue:String, val answeredAt:String,val answeredDuration:Int) {
}