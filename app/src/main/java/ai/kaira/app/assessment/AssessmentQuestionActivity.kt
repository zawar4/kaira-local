package ai.kaira.app.assessment

import ai.kaira.app.R
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.databinding.ActivityAssessmentQuestionBinding
import ai.kaira.app.utils.LanguageConfig
import ai.kaira.app.utils.UIUtils
import ai.kaira.app.utils.Consts.Companion.ASSESSMENT_TYPE
import ai.kaira.domain.assessment.model.Assessment
import ai.kaira.domain.assessment.model.AssessmentAnswerClick
import ai.kaira.domain.assessment.model.AssessmentType
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AssessmentQuestionActivity : AppCompatActivity() {
    lateinit var activityAssessmentQuestionBinding : ActivityAssessmentQuestionBinding
    lateinit var assessmentViewModel: AssessmentViewModel
    var answerClickCallback: MutableLiveData<AssessmentAnswerClick> = MutableLiveData()

    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAssessmentQuestionBinding = DataBindingUtil.setContentView(this,R.layout.activity_assessment_question)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        assessmentViewModel  = ViewModelProvider(this, viewModelFactory).get(AssessmentViewModel::class.java)
        lateinit var assessmentType : AssessmentType
        val languageLocale = LanguageConfig.getLanguageLocale(applicationContext)
        if(intent != null && intent.hasExtra(ASSESSMENT_TYPE)){
            assessmentType = intent.getSerializableExtra(ASSESSMENT_TYPE) as AssessmentType

            assessmentViewModel.fetchAssessments(assessmentType,languageLocale).observe(this){
                setAssessmentQuestionsView(assessmentType)
                setAssessmentQuestionsData((it))
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
            answersAdapter.addAnswers(ArrayList(it))
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

        assessmentViewModel.onConnectivityError().observe(this, {
            UIUtils.networkConnectivityAlert(this)
        })

        assessmentViewModel.onError().observe(this,{
            UIUtils.networkCallAlert(this,it)
        })

        assessmentViewModel.onActivityFinish().observe(this){
            finish()
        }

        activityAssessmentQuestionBinding.backBtn.setOnClickListener {
            onBackPressed()
        }

        activityAssessmentQuestionBinding.closeBtn.setOnClickListener {
            finish()
        }

        assessmentViewModel.onSubmitAnswer().observe(this){

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