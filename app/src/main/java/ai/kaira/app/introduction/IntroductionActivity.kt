package ai.kaira.app.introduction

import ai.kaira.app.R
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.assessment.AssessmentActivity
import ai.kaira.app.databinding.ActivityIntroductionBinding
import ai.kaira.app.utils.LanguageConfig.Companion.getLanguageLocale
import ai.kaira.app.utils.UIUtils.Companion.networkCallAlert
import ai.kaira.app.utils.UIUtils.Companion.networkConnectivityAlert
import ai.kaira.app.utils.Consts.Companion.ASSESSMENT_TYPE
import ai.kaira.app.utils.Extensions.Companion.isConnectedToInternet
import ai.kaira.domain.assessment.model.AssessmentType
import ai.kaira.domain.introduction.model.User
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.text.Editable
import android.text.TextWatcher
import android.view.View.*
import android.view.inputmethod.EditorInfo
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


    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    override fun onResume() {
        super.onResume()
        introductionViewModel.fetchUser().observe(this){
            if(it != null){
                if(introductionViewModel.isAssessmentCompleted(AssessmentType.PSYCHOLOGICAL.value) ||
                        introductionViewModel.isAssessmentCompleted(AssessmentType.FINANCIAL.value)){


                    introductionViewModel.displayIntroductionFields(false)
                    if(!introductionViewModel.isAvatarHeightReduced()){
                        introductionViewModel.reduceAvatarHeight(true)
                    }
                    introductionViewModel.displayAssessmentFields(true,it)
                    if(introductionViewModel.isAssessmentCompleted(AssessmentType.PSYCHOLOGICAL.value)){
                        completePsychologicalAssessment()
                        introductionBinding.financialAssessmentLayout.isEnabled = true
                        introductionBinding.headingTv.text = getString(R.string.introduction_assessment_title_2)
                        introductionBinding.descriptionTv.text = getString(R.string.introduction_assessment_detail_2)
                        enableFinancialAssessment()
                    }
                    if(introductionViewModel.isAssessmentCompleted(AssessmentType.FINANCIAL.value)){
                        completeFinancialAssessment()
                    }

                }
            }
            displayLayoutVisibleAnimation()
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        introductionBinding = DataBindingUtil.setContentView(this, R.layout.activity_introduction)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        introductionViewModel = ViewModelProvider(this, viewModelFactory).get(IntroductionViewModel::class.java)
        introductionViewModel.deleteUserOldAssessmentsAnswers()
        introductionBinding.firstNameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (introductionBinding.firstNameEt.text.isNotEmpty() && introductionBinding.submitButton.visibility == INVISIBLE) {
                    introductionBinding.submitButton.visibility = VISIBLE
                } else if (introductionBinding.firstNameEt.text.isEmpty() && introductionBinding.submitButton.visibility == VISIBLE) {
                    introductionBinding.submitButton.visibility = INVISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        introductionBinding.firstNameEt.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                submit()
            }
            false;
        }

        introductionBinding.submitButton.setOnClickListener {
            submit()
        }

        introductionViewModel.onError().observe(this, {
            networkCallAlert(this, it)
        })

        introductionViewModel.onConnectivityError().observe(this, {
            networkConnectivityAlert(this)
        })

        introductionBinding.psychologicalAssessmentLayout?.setOnClickListener {
            val intent = Intent(this,AssessmentActivity::class.java)
            intent.putExtra(ASSESSMENT_TYPE,AssessmentType.PSYCHOLOGICAL)
            startActivity(intent)
        }

        introductionBinding.financialAssessmentLayout.setOnClickListener {
            val intent = Intent(this,AssessmentActivity::class.java)
            intent.putExtra(ASSESSMENT_TYPE,AssessmentType.FINANCIAL)
            startActivity(intent)
        }

        introductionViewModel.onCreateUser().observe(this,{
            if(!introductionViewModel.isAvatarHeightReduced()){
                introductionViewModel.reduceAvatarHeight(true)
            }
            introductionViewModel.displayIntroductionFields(false)
            introductionViewModel.displayAssessmentFields(true,it)

        })

        introductionViewModel.onDisplayAssessmentFields().observe(this){
            displayAssessmentsFields(it)
        }

        introductionViewModel.onHideAssessmentFields().observe(this){
            hideAssessmentsFields()
        }

        introductionViewModel.onDisplayIntroductionFields().observe(this){
            if(it == true){
                displayIntroductionFields()
            }else{
                hideIntroductionFields()
            }
        }

        introductionViewModel.onAvatarHeightChange().observe(this){
            onAvatarHeightChange(it)
        }
    }

    private fun submit(){
        val firstName: String = introductionBinding.firstNameEt.text.toString()
        val languageLocale: String = getLanguageLocale(applicationContext)
        if(isConnectedToInternet()){
            if(firstName.isNotBlank()){
                introductionViewModel.createUser(firstName, languageLocale)
                introductionViewModel.deleteUserOldAssessmentsAnswers()
            }

        }else{
            networkConnectivityAlert(this)
        }
    }


    private fun hideIntroductionFields(){
        introductionBinding.firstNameEt.visibility = INVISIBLE
        introductionBinding.submitButton.visibility = INVISIBLE
    }

    private fun displayAssessmentsFields(user: User){

        val fadeTransition : Transition = Fade()
        val boundTransition : Transition = ChangeBounds()
        fadeTransition.duration = 500
        val transitionSet = TransitionSet()
        transitionSet.addTransition(fadeTransition).addTransition(boundTransition)
        TransitionManager.beginDelayedTransition(introductionBinding.introductionLayoutParent,transitionSet)


        introductionBinding.psychologicalAssessmentLayout?.visibility = VISIBLE
        introductionBinding.financialAssessmentLayout.visibility = VISIBLE

        introductionBinding.financialAssessmentLayout.setBackgroundResource(R.drawable.light_gray_round_rectangle)
        introductionBinding.financialAssessmentNumTv?.setBackgroundResource(R.drawable.dark_gray_circle)
        introductionBinding.financialAssessmentNumTv?.setTextColor(ContextCompat.getColor(applicationContext,android.R.color.white))
        introductionBinding.financialAssessmentNumTv?.setText(R.string._2)
        introductionBinding.financialAssessmentTv?.setTextColor(ContextCompat.getColor(applicationContext,R.color.medium_gray))
        introductionBinding.financialAssessmentLayout.isEnabled = false
        introductionBinding.psychologicalAssessmentNumTv?.setText(R.string._1)

        introductionBinding.headingTv.text = getString(R.string.introduction_assessment_title_1, user.firstName)
        introductionBinding.descriptionTv.text = getString(R.string.introduction_assessment_detail_1)


    }

    private fun onAvatarHeightChange(reduce:Boolean){
        val fadeTransition : Transition = Fade()
        val boundTransition : Transition = ChangeBounds()
        fadeTransition.duration = 1000
        val transitionSet = TransitionSet()
        transitionSet.addTransition(fadeTransition).addTransition(boundTransition)
        TransitionManager.beginDelayedTransition(introductionBinding.introductionLayoutParent,transitionSet)
        if(reduce){
            val imageHeight = introductionBinding.avatarIm.layoutParams.height
            introductionBinding.avatarIm.layoutParams.height = imageHeight/2
            introductionBinding.avatarIm.layoutParams.width = introductionBinding.avatarIm.layoutParams.height
        }else{
            val imageHeight = introductionBinding.avatarIm.layoutParams.height
            introductionBinding.avatarIm.layoutParams.height = imageHeight * 2
            introductionBinding.avatarIm.layoutParams.width = introductionBinding.avatarIm.layoutParams.height
        }

    }

    private fun displayIntroductionFields(){
        val fadeTransition : Transition = Fade()
        val boundTransition : Transition = ChangeBounds()
        fadeTransition.duration = 500
        val transitionSet = TransitionSet()
        transitionSet.addTransition(fadeTransition).addTransition(boundTransition)
        TransitionManager.beginDelayedTransition(introductionBinding.introductionLayoutParent,transitionSet)
        introductionBinding.firstNameEt.visibility = VISIBLE
        if(introductionBinding.firstNameEt.text.toString().isNotBlank()){
            introductionBinding.submitButton.visibility = VISIBLE
        }else{
            introductionBinding.submitButton.visibility = INVISIBLE
        }
        introductionBinding.headingTv.setText(R.string.introduction_welcome_title)
        introductionBinding.descriptionTv.setText(R.string.introduction_welcome_description)
    }

    private fun hideAssessmentsFields(){
        introductionBinding.psychologicalAssessmentLayout?.visibility = INVISIBLE
        introductionBinding.financialAssessmentLayout.visibility = INVISIBLE
    }

    private fun completeFinancialAssessment(){
        introductionBinding.financialAssessmentNumTv?.setText(R.string.tick)
    }
    private fun enableFinancialAssessment(){
        introductionBinding.financialAssessmentLayout.setBackgroundResource(R.drawable.fourth_filled_b_round_rectangle)
        introductionBinding.financialAssessmentNumTv?.setBackgroundResource(R.drawable.kaira_forth_filled_circle)
        introductionBinding.financialAssessmentNumTv?.setTextColor(ContextCompat.getColor(this,android.R.color.white))
        introductionBinding.financialAssessmentTv?.setTextColor(ContextCompat.getColor(this,android.R.color.black))
    }

    private fun completePsychologicalAssessment(){
        introductionBinding.psychologicalAssessmentNumTv?.setText(R.string.tick)
    }

    override fun onBackPressed() {
        if(introductionViewModel.isAssessmentFieldsDisplayed()){
            introductionViewModel.displayAssessmentFields(false)
            introductionViewModel.displayIntroductionFields(true)
            introductionViewModel.deleteUserOldAssessmentsAnswers()
            if(introductionViewModel.isAvatarHeightReduced())
                introductionViewModel.reduceAvatarHeight(false)
            hideAssessmentsFields()
        }else{
            super.onBackPressed();
        }
    }
    private fun displayLayoutVisibleAnimation(){
        introductionBinding.introductionLayoutParent.animate().alpha(1.0f).duration = 800
    }

}