package ai.kaira.domain.assessment.respository

import ai.kaira.domain.KairaResult
import ai.kaira.domain.assessment.model.*
import ai.kaira.domain.introduction.model.User
import androidx.lifecycle.MutableLiveData

interface AssessmentRepository {
    fun getFinancialAssessment(locale:String): MutableLiveData<Assessment>
    fun getPsychologicalAssessment(locale:String): MutableLiveData<Assessment>
    fun submitAssessmentAnswer(userId: String, question: AssessmentQuestion, answer: AssessmentAnswer?, assessment: Assessment): MutableLiveData<KairaResult<Unit>>
    fun isQuestionAlreadyAnswered(assessmentId:Int,assessmentType:Int,questionId:Int):Int
    fun saveSelectedAssessmentAnswer(assessmentId:Int,assessmentType:Int,questionId:Int,assessmentAnswerPosition:Int)
    fun deleteUserOldAssessmentsAnswers()
    fun markAssessmentAsComplete(assessmentType: Int)
    fun isAssessmentCompleted(assessmentType: Int):Boolean
    fun computeFinancialAssessmentProfile(assessmentType: Int,userId:String) : MutableLiveData<KairaResult<FinancialProfile>>
    fun computePsychologicalAssessmentProfile(assessmentType: Int,userId:String) : MutableLiveData<KairaResult<PsychologicalProfile>>
    fun savePsychologicalAssessmentProfile(psychologicalProfile: PsychologicalProfile)
    fun saveFinancialAssessmentProfile(financialProfile: FinancialProfile)
    fun fetchPsychologicalAssessmentProfileSync(): PsychologicalProfile?
    fun fetchPsychologicalAssessmentProfileAsync(): MutableLiveData<PsychologicalProfile?>
    fun fetchFinancialAssessmentProfileAsync(): MutableLiveData<FinancialProfile?>
    fun fetchFinancialAssessmentProfileSync(): FinancialProfile?
    fun processAssessmentProfiles(languageLocale:String,userId: String,financialAssessmentProfile: FinancialProfile,psychologicalAssessmentProfile: PsychologicalProfile): MutableLiveData<KairaResult<Strategy>>
    fun saveStrategy(strategy: Strategy)
    fun fetchStrategy():MutableLiveData<Strategy?>
}