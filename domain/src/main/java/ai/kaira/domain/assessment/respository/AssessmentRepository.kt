package ai.kaira.domain.assessment.respository

import ai.kaira.domain.Result
import ai.kaira.domain.assessment.model.Assessment
import ai.kaira.domain.assessment.model.AssessmentAnswer
import ai.kaira.domain.assessment.model.AssessmentQuestion
import ai.kaira.domain.introduction.model.User
import androidx.lifecycle.MutableLiveData

interface AssessmentRepository {
    fun getFinancialAssessment(locale:String): MutableLiveData<Assessment>
    fun getPsychologicalAssessment(locale:String): MutableLiveData<Assessment>
    fun submitAssessmentAnswer(user: User, question: AssessmentQuestion, answer: AssessmentAnswer?, assessment: Assessment): MutableLiveData<Result<Unit>>
    fun isQuestionAlreadyAnswered(assessmentId:Int,assessmentType:Int,questionId:Int):Int
    fun saveSelectedAssessmentAnswer(assessmentId:Int,assessmentType:Int,questionId:Int,assessmentAnswerPosition:Int)
}