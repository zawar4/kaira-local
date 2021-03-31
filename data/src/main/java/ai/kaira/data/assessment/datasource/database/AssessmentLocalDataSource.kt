package ai.kaira.data.assessment.datasource.database

import ai.kaira.domain.assessment.model.Assessment
import ai.kaira.domain.assessment.model.FinancialProfile
import ai.kaira.domain.assessment.model.PsychologicalProfile
import ai.kaira.domain.assessment.model.Strategy
import androidx.lifecycle.MutableLiveData

interface AssessmentLocalDataSource {
    fun getFinancialAssessment(locale:String): MutableLiveData<Assessment>
    fun getPsychologicalAssessment(locale:String): MutableLiveData<Assessment>
    fun isQuestionAlreadyAnswered(assessmentId: Int, assessmentType: Int, questionId: Int): Int
    fun saveSelectedAssessmentAnswer(assessmentId: Int, assessmentType: Int, questionId: Int, assessmentAnswerPosition: Int)
    fun deleteUserOldAssessmentsAnswers()
    fun markAssessmentAsComplete(assessmentType: Int)
    fun isAssessmentCompleted(assessmentType: Int):Boolean
    fun savePsychologicalAssessmentProfile(psychologicalProfile: PsychologicalProfile)
    fun saveFinancialAssessmentProfile(financialProfile: FinancialProfile)
    fun fetchPsychologicalAssessmentProfileAsync(): MutableLiveData<PsychologicalProfile?>
    fun fetchPsychologicalAssessmentProfileSync(): PsychologicalProfile?
    fun fetchFinancialAssessmentProfileAsync(): MutableLiveData<FinancialProfile?>
    fun fetchFinancialAssessmentProfileSync(): FinancialProfile?
    fun saveStrategy(strategy: Strategy)
    fun fetchStrategy():MutableLiveData<Strategy?>
}