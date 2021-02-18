package ai.kaira.app.assessment

import ai.kaira.app.R
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.databinding.ActivityAssessmentBinding
import ai.kaira.app.utils.LanguageConfig
import ai.kaira.app.utils.di.Consts.Companion.ASSESSMENT_TYPE
import ai.kaira.domain.assessment.model.Assessment
import ai.kaira.domain.assessment.model.AssessmentType
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AssessmentActivity : AppCompatActivity() {

    lateinit var activityAssessmentBinding : ActivityAssessmentBinding
    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    lateinit var assessmentViewModel: AssessmentViewModel
    lateinit var assessmentType : AssessmentType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAssessmentBinding = DataBindingUtil.setContentView(this,R.layout.activity_assessment)
        assessmentViewModel  = ViewModelProvider(this, viewModelFactory).get(AssessmentViewModel::class.java)

        val languageLocale = LanguageConfig.getLanguageLocale(applicationContext)
        if(intent != null && intent.hasExtra(ASSESSMENT_TYPE)){
            assessmentType = intent.getSerializableExtra(ASSESSMENT_TYPE) as AssessmentType
            if(assessmentType ==AssessmentType.FINANCIAL) {
                assessmentViewModel.getFinancialAssessment(languageLocale).observe(this,{
                    setView(assessmentType)
                    setData((it))
                })
            }
            else if(assessmentType ==AssessmentType.PSYCHOLOGICAL){
                assessmentViewModel.getPsychologicalAssessment(languageLocale).observe(this,{
                    setView(assessmentType)
                    setData((it))
                })
            }else{
                finish()
            }
        }else{
            finish()
        }

    }


    private fun setView(assessmentType: AssessmentType){
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
    private fun setData(assessment:Assessment){
        activityAssessmentBinding.assessmentNumTv.text = assessment.type.toString()
        activityAssessmentBinding.assessmentDescriptionTv.text = assessment.description
        activityAssessmentBinding.assessmentDurationTv.text = "  ${assessment.duration} ${getString(R.string.mint)}"
        activityAssessmentBinding.assessmentDurationTv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_clock,0,0,0)
        var numOfQuestions = assessment.questions.size.toString()
        if(assessment.questions.size.toString().length == 1) {
            numOfQuestions  = "0$numOfQuestions"
        }
        activityAssessmentBinding.assessmentQuestionsTv.text = "  $numOfQuestions"
        activityAssessmentBinding.assessmentQuestionsTv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_question,0,0,0)

        activityAssessmentBinding.assessmentStartBtn?.setOnClickListener {
            var intent = Intent(this,AssessmentQuestionActivity::class.java)
            intent.putExtra(ASSESSMENT_TYPE,assessmentType)
            startActivity(intent)
        }
    }


}