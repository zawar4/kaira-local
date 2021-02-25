package ai.kaira.data.assessment.datasource.database

import ai.kaira.data.utils.LanguageConfig
import ai.kaira.domain.assessment.model.Assessment
import ai.kaira.domain.assessment.model.AssessmentType
import ai.kaira.domain.assessment.model.FinancialProfile
import ai.kaira.domain.assessment.model.PsychologicalProfile
import android.content.SharedPreferences
import android.content.res.AssetManager
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import javax.inject.Inject

class AssessmentLocalDataSourceImp @Inject constructor(private val assetManager: AssetManager,private val prefs: SharedPreferences) : AssessmentLocalDataSource {

    private val assessmentLiveData : MutableLiveData<Assessment> = MutableLiveData()
    override fun getFinancialAssessment(locale:String): MutableLiveData<Assessment> {
        var fileName = "financial_assessment_"
        if(locale == LanguageConfig.CANADIAN_FRENCH || locale == LanguageConfig.FRENCH){
            fileName +="fr_ca.json"
        }else if(locale == LanguageConfig.CANADIAN_ENGLISH){
            fileName +="en.json"
        }else{
            fileName +="en.json"
        }
        val assessmentText = assetManager.open(fileName).bufferedReader().use { it.readText() }
        val gson = Gson()
        val assessment = gson.fromJson(assessmentText, Assessment::class.java)
        assessmentLiveData.value = assessment
        return assessmentLiveData
    }

    override fun getPsychologicalAssessment(locale: String): MutableLiveData<Assessment> {
        var fileName = "psychological_assessment_"
        if(locale == LanguageConfig.CANADIAN_FRENCH || locale == LanguageConfig.FRENCH){
            fileName +="fr_ca.json"
        }else if(locale == LanguageConfig.CANADIAN_ENGLISH){
            fileName +="en.json"
        }else{
            fileName +="en.json"
        }
        val assessmentText = assetManager.open(fileName).bufferedReader().use { it.readText() }
        val gson = Gson()
        val assessment = gson.fromJson(assessmentText, Assessment::class.java)
        assessmentLiveData.value = assessment
        return assessmentLiveData
    }

    override fun isQuestionAlreadyAnswered(assessmentId: Int, assessmentType: Int, questionId: Int): Int {
        return prefs.getInt("$assessmentType$assessmentId$questionId",-1)
    }

    override fun saveSelectedAssessmentAnswer(assessmentId: Int, assessmentType: Int, questionId: Int, assessmentAnswerPosition: Int) {
        prefs.edit().putInt("$assessmentType$assessmentId$questionId",assessmentAnswerPosition).apply()
    }

    override fun deleteUserOldAssessmentsAnswers() {
        prefs.edit().clear().apply()
    }

    override fun markAssessmentAsComplete(assessmentType: Int) {
        prefs.edit().putBoolean("$assessmentType",true).apply()
    }

    override fun isAssessmentCompleted(assessmentType: Int):Boolean {
        return prefs.contains("$assessmentType")
    }

    override fun savePsychologicalAssessmentProfile(psychologicalProfile: PsychologicalProfile) {
        val gson = Gson()
        prefs.edit().putString("${AssessmentType.PSYCHOLOGICAL.value}",gson.toJson(psychologicalProfile)).apply()
    }

    override fun saveFinancialAssessmentProfile(financialProfile: FinancialProfile) {
        val gson = Gson()
        prefs.edit().putString("${AssessmentType.FINANCIAL.value}",gson.toJson(financialProfile)).apply()
    }


}