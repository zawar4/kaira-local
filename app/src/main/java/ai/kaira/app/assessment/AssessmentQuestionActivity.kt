package ai.kaira.app.assessment

import ai.kaira.app.R
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.databinding.ActivityAssessmentQuestionBinding
import ai.kaira.app.utils.LanguageConfig
import ai.kaira.app.utils.di.Consts.Companion.ASSESSMENT_TYPE
import ai.kaira.data.assessment.di.AssessmentModule
import ai.kaira.domain.assessment.model.Assessment
import ai.kaira.domain.assessment.model.AssessmentType
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AssessmentQuestionActivity : AppCompatActivity() {
    lateinit var activityAssessmentQuestionBinding : ActivityAssessmentQuestionBinding
    lateinit var assessmentViewModel: AssessmentViewModel

    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAssessmentQuestionBinding = DataBindingUtil.setContentView(this,R.layout.activity_assessment_question)
        assessmentViewModel  = ViewModelProvider(this, viewModelFactory).get(AssessmentViewModel::class.java)
        lateinit var assessmentType : AssessmentType
        val languageLocale = LanguageConfig.getLanguageLocale(applicationContext)
        if(intent != null && intent.hasExtra(ASSESSMENT_TYPE)){
            assessmentType = intent.getSerializableExtra(ASSESSMENT_TYPE) as AssessmentType
            if(assessmentType == AssessmentType.FINANCIAL) {
                assessmentViewModel.getFinancialAssessment(languageLocale).observe(this,{
                    setView(assessmentType)
                    setData((it))
                })
            }
            else if(assessmentType == AssessmentType.PSYCHOLOGICAL){
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
            activityAssessmentQuestionBinding.questionNumTv.setBackgroundResource(R.drawable.kaira_third_filled_rectangle);
        }else{
            activityAssessmentQuestionBinding.questionNumTv.setBackgroundResource(R.drawable.kaira_fourth_filled_rectangle);
        }
    }

    private fun setData(assessment:Assessment){
        activityAssessmentQuestionBinding.questionNumTv.text = "1 / 10"
        activityAssessmentQuestionBinding.assessmentDisclaimerTv.text = assessment.mention
        activityAssessmentQuestionBinding.assessmentQuestionDescriptionTv.text = assessment.questions[0].title
    }
}