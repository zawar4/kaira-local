package ai.kaira.data.assessment.model

data class AssessmentAnswerRequestParam (val userId: String,
                                         val assessmentId:Int,
                                         val assessmentVersion:Int,
                                         val assessmentType:Int,
                                         val questionId:Int,
                                         val questionType:Int,
                                         val answerId:Int,
                                         val answerValue:Int,
                                         val answeredAt:String,
                                         val answeredDuration:Double) {


}