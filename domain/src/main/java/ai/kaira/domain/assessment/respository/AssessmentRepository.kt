package ai.kaira.domain.assessment.respository

import ai.kaira.domain.Result
import ai.kaira.domain.assessment.model.*
import ai.kaira.domain.introduction.model.User
import androidx.lifecycle.MutableLiveData

interface AssessmentRepository {
    fun getFinancialAssessment(locale:String): MutableLiveData<Assessment>
    fun getPsychologicalAssessment(locale:String): MutableLiveData<Assessment>
    fun submitAssessmentAnswer(user: User, question: AssessmentQuestion, answer: AssessmentAnswer?, assessment: Assessment): MutableLiveData<Result<Unit>>
    fun isQuestionAlreadyAnswered(assessmentId:Int,assessmentType:Int,questionId:Int):Int
    fun saveSelectedAssessmentAnswer(assessmentId:Int,assessmentType:Int,questionId:Int,assessmentAnswerPosition:Int)
    fun deleteUserOldAssessmentsAnswers()
    fun markAssessmentAsComplete(assessmentType: Int)
    fun isAssessmentCompleted(assessmentType: Int):Boolean
    fun computeFinancialAssessmentProfile(assessmentType: Int,userId:String) : MutableLiveData<Result<FinancialProfileResponse>>
    fun computePsychologicalAssessmentProfile(assessmentType: Int,userId:String) : MutableLiveData<Result<PsychologicalProfileResponse>>
}