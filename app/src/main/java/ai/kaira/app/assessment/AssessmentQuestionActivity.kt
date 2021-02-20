package ai.kaira.app.assessment

import ai.kaira.app.R
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.databinding.ActivityAssessmentQuestionBinding
import ai.kaira.app.utils.LanguageConfig
import ai.kaira.app.utils.di.Consts.Companion.ASSESSMENT_TYPE
import ai.kaira.data.assessment.di.AssessmentModule
import ai.kaira.domain.assessment.model.Assessment
import ai.kaira.domain.assessment.model.AssessmentAnswer
import ai.kaira.domain.assessment.model.AssessmentType
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout.VERTICAL
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AssessmentQuestionActivity : AppCompatActivity() {
    lateinit var activityAssessmentQuestionBinding : ActivityAssessmentQuestionBinding
    lateinit var assessmentViewModel: AssessmentViewModel
    var answerClickCallback: MutableLiveData<AssessmentAnswer> = MutableLiveData()

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
            when (assessmentType) {
                AssessmentType.FINANCIAL -> {
                    assessmentViewModel.fetchFinancialAssessment(languageLocale).observe(this,{
                        setAssessmentQuestionsView(assessmentType)
                        setAssessmentQuestionsData((it))
                    })
                }
                AssessmentType.PSYCHOLOGICAL -> {
                    assessmentViewModel.fetchPsychologicalAssessment(languageLocale).observe(this,{
                        setAssessmentQuestionsView(assessmentType)
                        setAssessmentQuestionsData((it))
                    })
                }
                else -> {
                    finish()
                }
            }
        }else{
            finish()
        }
    }

    private fun setAssessmentQuestionsView(assessmentType: AssessmentType){
        if(assessmentType == AssessmentType.PSYCHOLOGICAL){
            activityAssessmentQuestionBinding.questionNumTv.setBackgroundResource(R.drawable.kaira_third_filled_rectangle);
        }else{
            activityAssessmentQuestionBinding.questionNumTv.setBackgroundResource(R.drawable.kaira_fourth_filled_rectangle);
        }
    }

    private fun setAssessmentQuestionsData(assessment:Assessment){

        assessmentViewModel.loadFirstQuestion()
        activityAssessmentQuestionBinding.assessmentDisclaimerTv.text = assessment.mention
        lateinit var assessmentType : AssessmentType
        if(assessment.type == AssessmentType.PSYCHOLOGICAL.value)
            assessmentType =  AssessmentType.PSYCHOLOGICAL
        else{
            assessmentType =  AssessmentType.FINANCIAL
        }
        val answersAdapter = AnswersRecyclerViewAdapter(ArrayList(),answerClickCallback,assessmentType)
        activityAssessmentQuestionBinding.answerRecyclerView.layoutManager = LinearLayoutManager(this)
        activityAssessmentQuestionBinding.answerRecyclerView.adapter = answersAdapter
        activityAssessmentQuestionBinding.submitBtn.setOnClickListener {
            assessmentViewModel.loadNextQuestion()
        }
        assessmentViewModel.setQuestionAnswers().observe(this){
            answersAdapter.answers = ArrayList(it)
            answersAdapter.notifyDataSetChanged()
            activityAssessmentQuestionBinding.scrollView.scrollTo(0,0)
        }
        assessmentViewModel.setQuestionTitle().observe(this){
            activityAssessmentQuestionBinding.assessmentQuestionDescriptionTv.text = it
        }

        assessmentViewModel.setQuestionNumber().observe(this){
            activityAssessmentQuestionBinding.questionNumTv.text = it
        }

        answerClickCallback.observe(this){
            assessmentViewModel.setQuestionAnswered(it)
        }

        activityAssessmentQuestionBinding.submitBtn?.setOnClickListener {
            assessmentViewModel.loadNextQuestion()

        }

        assessmentViewModel.setSubmitButton().observe(this){
            activityAssessmentQuestionBinding.submitBtn.isEnabled = it
        }

    }

    override fun onBackPressed() {
        if(!assessmentViewModel.isFirstQuestion()){
            assessmentViewModel.loadPreviousQuestion()
        }else{
            super.onBackPressed();
        }
    }
}