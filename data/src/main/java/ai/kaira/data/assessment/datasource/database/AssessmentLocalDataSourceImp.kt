package ai.kaira.data.assessment.datasource.database

import ai.kaira.data.utils.Consts
import ai.kaira.data.utils.LanguageConfig
import ai.kaira.domain.assessment.model.*
import android.content.SharedPreferences
import android.content.res.AssetManager
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import javax.inject.Inject

class AssessmentLocalDataSourceImp @Inject constructor(private val assetManager: AssetManager,private val prefs: SharedPreferences) : AssessmentLocalDataSource {

    private val assessmentLiveData : MutableLiveData<Assessment> = MutableLiveData()
    private val psychologicalAssessmentProfileLiveData = MutableLiveData<PsychologicalProfile?>()
    private val financialAssessmentProfileLiveData = MutableLiveData<FinancialProfile?>()
    private val strategyLiveData = MutableLiveData<Strategy?>()
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
        assessmentLiveData.value =(assessment)
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


    override fun fetchPsychologicalAssessmentProfileAsync(): MutableLiveData<PsychologicalProfile?> {
        fetchPsychologicalAssessmentProfileSync().let{
            psychologicalAssessmentProfileLiveData.value = it
        }
        return psychologicalAssessmentProfileLiveData

    }


    override fun fetchPsychologicalAssessmentProfileSync(): PsychologicalProfile? {
        val psychologicalProfileText = prefs.getString("${AssessmentType.PSYCHOLOGICAL.value}","")
        psychologicalProfileText?.let{
            if(it?.isNotBlank()){
                val gson = Gson()
                val psychologicalProfile = gson.fromJson(psychologicalProfileText, PsychologicalProfile::class.java)
                return psychologicalProfile
            }
        }
        return null

    }

    override fun fetchFinancialAssessmentProfileAsync(): MutableLiveData<FinancialProfile?> {
        fetchFinancialAssessmentProfileSync()?.let{
            financialAssessmentProfileLiveData.value = it
        }
        return financialAssessmentProfileLiveData
    }

    override fun fetchFinancialAssessmentProfileSync(): FinancialProfile? {
        val financialProfileText = prefs.getString("${AssessmentType.FINANCIAL.value}","")
        financialProfileText?.let{
            if(it.isNotBlank()){
                val gson = Gson()
                val financialProfile = gson.fromJson(financialProfileText, FinancialProfile::class.java)
                return financialProfile
            }
        }
        return null
    }

    override fun saveStrategy(strategy: Strategy){
        val gson = Gson()
        prefs.edit().putString(Consts.STRATEGY,gson.toJson(strategy)).apply()
    }

    override fun fetchStrategy(): MutableLiveData<Strategy?> {
        val strategyText = prefs.getString(Consts.STRATEGY,"")
        strategyText?.let{
            if(it.isNotBlank()){
                val gson = Gson()
                val strategy = gson.fromJson(strategyText, Strategy::class.java)
                strategyLiveData.value = strategy
            }else{
                strategyLiveData.value = null
            }
        }
        return strategyLiveData
    }


}