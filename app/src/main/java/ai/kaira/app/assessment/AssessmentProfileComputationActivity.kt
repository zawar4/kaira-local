package ai.kaira.app.assessment

import ai.kaira.app.R
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.databinding.ActivityAssessmentProfileComputationBinding
import ai.kaira.app.utils.Consts
import ai.kaira.app.utils.Extensions.Companion.decreaseViewSize
import ai.kaira.app.utils.Extensions.Companion.increaseViewSize
import ai.kaira.app.utils.UIUtils
import ai.kaira.domain.assessment.model.AssessmentType
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.transition.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class AssessmentProfileComputationActivity : AppCompatActivity() {

    lateinit var assessmentViewModel: AssessmentViewModel
    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    lateinit var activityAssessmentComputationBinding: ActivityAssessmentProfileComputationBinding

    private val animationDotThree = Timer()
    private val animationDotTwo = Timer()
    private val animationDotOne = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAssessmentComputationBinding = DataBindingUtil.setContentView(this, R.layout.activity_assessment_profile_computation)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        assessmentViewModel  = ViewModelProvider(this, viewModelFactory).get(AssessmentViewModel::class.java)
        lateinit var assessmentType : AssessmentType
        val maxHeight = activityAssessmentComputationBinding.dotOneIm.layoutParams.height
        val minHeight = activityAssessmentComputationBinding.dotOneIm.layoutParams.height/2
        if(intent != null && intent.hasExtra(Consts.ASSESSMENT_TYPE)) {
            assessmentType = intent.getSerializableExtra(Consts.ASSESSMENT_TYPE) as AssessmentType
            if(assessmentType == AssessmentType.FINANCIAL){
                assessmentViewModel.computeFinancialAssessmentProfile(assessmentType.value)
            }else if(assessmentType == AssessmentType.PSYCHOLOGICAL){
                assessmentViewModel.computePsychologicalAssessmentProfile(assessmentType.value)
            }

            assessmentViewModel.onFinancialAssessmentProfileComputed().observe(this){
            }
            assessmentViewModel.onPsychologicalAssessmentProfileComputed().observe(this){

            }

            assessmentViewModel.onActivityFinish().observe(this){
                finish()
            }

            assessmentViewModel.onError().observe(this){
                UIUtils.networkCallAlert(this, it)
                stopLoadingAnimation()
                resetLoadingAnimation(minHeight)
            }

            assessmentViewModel.onConnectivityError().observe(this){
                UIUtils.networkConnectivityAlert(this)
                stopLoadingAnimation()
                resetLoadingAnimation(minHeight)
            }

            startLoadingAnimation(maxHeight, minHeight)
            Handler(Looper.getMainLooper()).postDelayed({
                stopLoadingAnimation()
                resetLoadingAnimation(minHeight)
            }, 5000)

        }

    }

    private fun startLoadingAnimation(maxHeight: Int, minHeight: Int){
        var isDotOneDecreased = false
        animationDotOne.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if (!isDotOneDecreased) {
                        activityAssessmentComputationBinding.dotOneIm.decreaseViewSize(500, maxHeight, minHeight)
                        isDotOneDecreased = true
                    } else {
                        activityAssessmentComputationBinding.dotOneIm.increaseViewSize( 500, maxHeight, minHeight)
                        isDotOneDecreased = false
                    }

                }
            }

        }, 0, 500)

        var isDotTwoDecreased = false
        animationDotTwo.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if (!isDotTwoDecreased) {
                        activityAssessmentComputationBinding.dotTwoIm.decreaseViewSize(500, maxHeight, minHeight)
                        isDotTwoDecreased = true
                    } else {
                        activityAssessmentComputationBinding.dotTwoIm.increaseViewSize( 500, maxHeight, minHeight)
                        isDotTwoDecreased = false
                    }

                }
            }

        }, 500, 500)

        var isDotThreeDecreased = false
        animationDotThree.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if (!isDotThreeDecreased) {
                        activityAssessmentComputationBinding.dotThreeIm.decreaseViewSize(500, maxHeight, minHeight)
                        isDotThreeDecreased = true
                    } else {
                        activityAssessmentComputationBinding.dotThreeIm.increaseViewSize( 500, maxHeight, minHeight)
                        isDotThreeDecreased = false
                    }
                }
            }

        }, 0, 500)
    }

    private fun resetLoadingAnimation(height:Int){
        val fadeTransition : Transition = Fade()
        val boundTransition : Transition = ChangeBounds()
        fadeTransition.duration = 500
        val transitionSet = TransitionSet()
        transitionSet.addTransition(fadeTransition).addTransition(boundTransition)
        TransitionManager.beginDelayedTransition(activityAssessmentComputationBinding.assessmentProfileComputationParent,transitionSet)
        activityAssessmentComputationBinding.dotThreeIm.layoutParams.height = height
        activityAssessmentComputationBinding.dotThreeIm.layoutParams.width = height
        activityAssessmentComputationBinding.dotTwoIm.layoutParams.height = height
        activityAssessmentComputationBinding.dotTwoIm.layoutParams.width = height
        activityAssessmentComputationBinding.dotOneIm.layoutParams.height = height
        activityAssessmentComputationBinding.dotOneIm.layoutParams.width = height
    }
    private fun stopLoadingAnimation(){
        animationDotThree.cancel()
        animationDotTwo.cancel()
        animationDotOne.cancel()
    }


}