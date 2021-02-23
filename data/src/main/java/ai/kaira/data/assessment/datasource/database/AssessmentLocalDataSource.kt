package ai.kaira.data.assessment.datasource.database

import ai.kaira.domain.assessment.model.Assessment
import androidx.lifecycle.MutableLiveData

interface AssessmentLocalDataSource {
    fun getFinancialAssessment(locale:String): MutableLiveData<Assessment>
    fun getPsychologicalAssessment(locale:String): MutableLiveData<Assessment>
    fun isQuestionAlreadyAnswered(assessmentId: Int, assessmentType: Int, questionId: Int): Int
    fun saveSelectedAssessmentAnswer(assessmentId: Int, assessmentType: Int, questionId: Int, assessmentAnswerPosition: Int)
    fun deleteUserOldAssessmentsAnswers()
    fun markAssessmentAsComplete(assessmentType: Int)
    fun isAssessmentCompleted(assessmentType: Int):Boolean
}