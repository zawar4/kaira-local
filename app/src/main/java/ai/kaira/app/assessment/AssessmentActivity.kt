package ai.kaira.app.assessment

import ai.kaira.app.R
import ai.kaira.app.databinding.ActivityAssessmentBinding
import ai.kaira.app.utils.Extensions.Companion.readFileText
import ai.kaira.app.utils.LanguageConfig
import ai.kaira.domain.assessment.model.Assessment
import ai.kaira.domain.assessment.model.AssessmentType
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson

class AssessmentActivity : AppCompatActivity() {

    lateinit var activityAssessmentBinding : ActivityAssessmentBinding
    private val ASSESSMENT_TYPE : String = "ASSESSMENT_TYPE"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAssessmentBinding = DataBindingUtil.setContentView(this,R.layout.activity_assessment)

        lateinit var assessmentType : AssessmentType
        lateinit var fileName : String
        if(intent != null && intent.hasExtra(ASSESSMENT_TYPE)){
            assessmentType = intent.getSerializableExtra(ASSESSMENT_TYPE) as AssessmentType
            if(assessmentType ==AssessmentType.FINANCIAL) {
                fileName = "financial_assessment_"
            }
            else{
                fileName = "psychological_assessment_"
            }
        }else{
            finish()
        }
        val currentLanguageLocale = LanguageConfig.getLanguageLocale(applicationContext)
        if((currentLanguageLocale == LanguageConfig.CANADIAN_FRENCH || currentLanguageLocale == LanguageConfig.FRENCH)){
            fileName +="fr_ca.json"
        }else if(currentLanguageLocale == LanguageConfig.CANADIAN_ENGLISH){
            fileName +="en.json"
        }
        else{
            fileName +="en.json"
        }
        val jsonText: String = readFileText(fileName)
        var gson = Gson()
        var assessment = gson.fromJson(jsonText, Assessment::class.java)
        assessment.type = assessmentType
        setView(assessmentType)
        setData((assessment))

    }


    fun setView(assessmentType: AssessmentType){
        if(assessmentType == AssessmentType.PSYCHOLOGICAL){
            activityAssessmentBinding.assessmentDisclaimerTv.text = getString(R.string.psychological_assessment_disclaimer)
            activityAssessmentBinding.assessmentTitleTv.text = getString(R.string.assessement_psychological_title)
            activityAssessmentBinding.background.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.kairaThirdFillASemiA))
            activityAssessmentBinding.assessmentNumTv.background = ContextCompat.getDrawable(applicationContext,R.drawable.kaira_third_filled_circle)
            activityAssessmentBinding.assessmentDurationTv.background = ContextCompat.getDrawable(applicationContext,R.drawable.karia_third_filled_a_semi_b_round_rectangle)
            activityAssessmentBinding.assessmentQuestionsTv.background = ContextCompat.getDrawable(applicationContext,R.drawable.karia_third_filled_a_semi_b_round_rectangle)

        }else{
            activityAssessmentBinding.assessmentDisclaimerTv.text = getString(R.string.financial_assessment_disclaimer)
            activityAssessmentBinding.assessmentTitleTv.text = getString(R.string.assessement_financial_title)
            activityAssessmentBinding.background.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.kairaFourthFillASemiA))
            activityAssessmentBinding.assessmentNumTv.background = ContextCompat.getDrawable(applicationContext,R.drawable.kaira_forth_filled_circle)
            activityAssessmentBinding.assessmentDurationTv.background = ContextCompat.getDrawable(applicationContext,R.drawable.karia_fourth_filled_a_semi_b_round_rectangle)
            activityAssessmentBinding.assessmentQuestionsTv.background = ContextCompat.getDrawable(applicationContext,R.drawable.karia_fourth_filled_a_semi_b_round_rectangle)
        }
    }
    fun setData(assessment:Assessment){
        activityAssessmentBinding.assessmentNumTv.text = assessment.type.type.toString()
        activityAssessmentBinding.assessmentDescriptionTv.text = assessment.description
        activityAssessmentBinding.assessmentDurationTv.text = "${assessment.duration} mint"
        activityAssessmentBinding.assessmentQuestionsTv.text = assessment.questions.size.toString()
    }


}