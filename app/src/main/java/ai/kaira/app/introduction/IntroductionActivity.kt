package ai.kaira.app.introduction

import ai.kaira.app.R
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.assessment.AssessmentActivity
import ai.kaira.app.databinding.ActivityIntroductionBinding
import ai.kaira.app.utils.LanguageConfig.Companion.getLanguageLocale
import ai.kaira.app.utils.UIUtils.Companion.networkCallAlert
import ai.kaira.app.utils.UIUtils.Companion.networkContectivityAlert
import ai.kaira.domain.assessment.model.AssessmentType
import ai.kaira.domain.introduction.model.User
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.transition.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class IntroductionActivity : AppCompatActivity() {

    lateinit var introductionBinding: ActivityIntroductionBinding
    lateinit var introductionViewModel: IntroductionViewModel
    private val ASSESSMENT_TYPE : String = "ASSESSMENT_TYPE"
    var displayedAssessmentFields : Boolean = false

    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        introductionBinding = DataBindingUtil.setContentView(this, R.layout.activity_introduction)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        introductionViewModel = ViewModelProvider(this, viewModelFactory).get(IntroductionViewModel::class.java)
        introductionBinding?.firstNameEt?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (introductionBinding?.firstNameEt?.text.isNotEmpty() && introductionBinding?.submitButton?.visibility == INVISIBLE) {
                    introductionBinding?.submitButton?.visibility = VISIBLE
                } else if (introductionBinding?.firstNameEt?.text.isEmpty() && introductionBinding?.submitButton?.visibility == VISIBLE) {
                    introductionBinding?.submitButton?.visibility = INVISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        introductionBinding.firstNameEt.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    submit()
                }
                return false;
            }
        })

        introductionBinding.submitButton.setOnClickListener {
            submit()
        }

        introductionViewModel.onError().observe(this, {
            networkCallAlert(this, it)
        })

        introductionViewModel.onConnectivityError().observe(this, {
            networkContectivityAlert(this)
        })

        introductionBinding.moneyMotivationAssessmentLayout.setOnClickListener {
            completeMoneyMotivationAssessment()
            val intent = Intent(this,AssessmentActivity::class.java)
            intent.putExtra(ASSESSMENT_TYPE,AssessmentType.PSYCHOLOGICAL)
            startActivity(intent)
            introductionBinding.financialAssessmentLayout.isEnabled = true
            enableFinancialAssessment()
        }

        introductionBinding.financialAssessmentLayout.setOnClickListener {
            val intent = Intent(this,AssessmentActivity::class.java)
            intent.putExtra(ASSESSMENT_TYPE,AssessmentType.FINANCIAL)
            startActivity(intent)
        }

        introductionViewModel.onCreateUser().observe(this,{
            hideIntroductionFields()
            displayAssessmentsFields(it)
            displayedAssessmentFields = true
        })

        displayLayoutVisibleAnimation()
    }

    private fun submit(){
        val firstName: String = introductionBinding.firstNameEt.text.toString()
        val languageLocale: String = getLanguageLocale(applicationContext)
        if(introductionViewModel.isConnectedToInternet()){
            introductionViewModel.createUser(firstName, languageLocale)
        }else{
            networkContectivityAlert(this)
        }
    }


    private fun hideIntroductionFields(){
        introductionBinding.firstNameEt.visibility = INVISIBLE
        introductionBinding.submitButton.visibility = INVISIBLE
    }

    private fun displayAssessmentsFields(user: User){

        val fadeTransition : Transition = Fade()
        val boundTransition : Transition = ChangeBounds()
        fadeTransition.duration = 2000
        val transitionSet = TransitionSet()
        transitionSet.addTransition(fadeTransition).addTransition(boundTransition)
        TransitionManager.beginDelayedTransition(introductionBinding.introductionLayoutParent,transitionSet)

        val imageHeight = introductionBinding.avatarIm.layoutParams.height
        introductionBinding.avatarIm.layoutParams.height = imageHeight/2
        introductionBinding.avatarIm.layoutParams.width = introductionBinding.avatarIm.layoutParams.height
        introductionBinding.moneyMotivationAssessmentLayout.setVisibility(VISIBLE)
        introductionBinding.financialAssessmentLayout.setVisibility(VISIBLE)
        introductionBinding.headingTv.setText(getString(R.string.introduction_assessment_title_1, user.firstName))
        introductionBinding.descriptionTv.setText(getString(R.string.introduction_assessment_detail_1))
        introductionBinding.financialAssessmentLayout.setBackgroundResource(R.drawable.light_gray_round_rectangle)
        introductionBinding.fininacialAssessmentNumTv.setBackgroundResource(R.drawable.dark_gray_circle)
        introductionBinding.fininacialAssessmentNumTv.setTextColor(ContextCompat.getColor(applicationContext,android.R.color.white))
        introductionBinding.fininacialAssessmentNumTv.setText(R.string._2)
        introductionBinding.fininacialAssessmentTv.setTextColor(ContextCompat.getColor(applicationContext,R.color.medium_gray))
        introductionBinding.financialAssessmentLayout?.isEnabled = false
        introductionBinding.moneyMotivationAssessmentNumTv.setText(R.string._1)

    }



    private fun displayIntroductionFields(){

        val fadeTransition : Transition = Fade()
        val boundTransition : Transition = ChangeBounds()
        fadeTransition.duration = 2000
        val transitionSet = TransitionSet()
        transitionSet.addTransition(fadeTransition).addTransition(boundTransition)
        TransitionManager.beginDelayedTransition(introductionBinding.introductionLayoutParent,transitionSet)
        val imageHeight = introductionBinding.avatarIm.layoutParams.height
        introductionBinding.avatarIm.layoutParams.height = imageHeight*2
        introductionBinding.avatarIm.layoutParams.width = introductionBinding.avatarIm.layoutParams.height
        introductionBinding.firstNameEt.setVisibility(VISIBLE)
        introductionBinding.submitButton.setVisibility(VISIBLE)
        introductionBinding.headingTv.setText(R.string.introduction_welcome_title)
        introductionBinding.descriptionTv.setText(R.string.introduction_welcome_description)
    }

    private fun hideAssessmentsFields(){
        introductionBinding.moneyMotivationAssessmentLayout.setVisibility(INVISIBLE)
        introductionBinding.financialAssessmentLayout.setVisibility(INVISIBLE)
    }

    private fun completeFinancialAssessment(){
        introductionBinding.fininacialAssessmentNumTv.setText(R.string.tick)
    }
    private fun enableFinancialAssessment(){
        introductionBinding.financialAssessmentLayout.setBackgroundResource(R.drawable.fourth_filled_b_round_rectangle)
        introductionBinding.fininacialAssessmentNumTv.setBackgroundResource(R.drawable.kaira_forth_filled_circle)
        introductionBinding.fininacialAssessmentNumTv.setTextColor(ContextCompat.getColor(this,android.R.color.white))
        introductionBinding.fininacialAssessmentTv.setTextColor(ContextCompat.getColor(this,android.R.color.black))
    }

    private fun completeMoneyMotivationAssessment(){
        introductionBinding.moneyMotivationAssessmentNumTv.setText(R.string.tick)
    }

    override fun onBackPressed() {
        if(displayedAssessmentFields){
            hideAssessmentsFields()
            displayIntroductionFields()
            displayedAssessmentFields = false
        }else{
            super.onBackPressed();
        }
    }
    private fun displayLayoutVisibleAnimation(){
        introductionBinding.introductionLayoutParent.animate().alpha(1.0f).duration = 800
    }

}