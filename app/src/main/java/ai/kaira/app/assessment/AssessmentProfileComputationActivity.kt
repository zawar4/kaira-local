package ai.kaira.app.assessment

import ai.kaira.app.R
import ai.kaira.app.application.ViewModelFactory
import ai.kaira.app.databinding.ActivityAssessmentProfileComputationBinding
import ai.kaira.app.utils.Consts
import ai.kaira.domain.assessment.model.AssessmentType
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.StrictMode
import android.view.View
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

    val animationDotThree = Timer()
    val animationDotTwo = Timer()
    val animationDotOne = Timer()

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

            startLoadingAnimation(maxHeight,minHeight)
            assessmentViewModel.onFinancialAssessmentProfileComputed().observe(this){
                stopLoading()
            }
            assessmentViewModel.onPsychologicalAssessmentProfileComputed().observe(this){
                stopLoading()
            }
        }






    }

    fun startLoadingAnimation(maxHeight:Int,minHeight:Int){
        var isDotOneDecreased = false
        animationDotOne.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if(!isDotOneDecreased){
                        decreaseViewSize(activityAssessmentComputationBinding.dotOneIm, 500,maxHeight,minHeight)
                        isDotOneDecreased = true
                    }else{
                        increaseViewSize(activityAssessmentComputationBinding.dotOneIm, 500,maxHeight,minHeight)
                        isDotOneDecreased = false
                    }

                }
            }

        }, 0,500)

        var isDotTwoDecreased = false
        animationDotTwo.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if(!isDotTwoDecreased){
                        decreaseViewSize(activityAssessmentComputationBinding.dotTwoIm, 500,maxHeight,minHeight)
                        isDotTwoDecreased = true
                    }else{
                        increaseViewSize(activityAssessmentComputationBinding.dotTwoIm, 500,maxHeight,minHeight)
                        isDotTwoDecreased = false
                    }

                }
            }

        }, 500,500)




        var isDotThreeDecreased = false
        animationDotThree.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if(!isDotThreeDecreased){
                        decreaseViewSize(activityAssessmentComputationBinding.dotThreeIm, 500,maxHeight,minHeight)
                        isDotThreeDecreased = true
                    }else{
                        increaseViewSize(activityAssessmentComputationBinding.dotThreeIm, 500,maxHeight,minHeight)
                        isDotThreeDecreased = false
                    }

                }
            }

        }, 1000,500)
    }

    fun stopLoading(){
        animationDotThree.cancel()
        animationDotTwo.cancel()
        animationDotOne.cancel()
    }
    private fun increaseViewSize(view: View, duration: Long,maxHeight:Int,minHeight:Int) {
        val valueAnimator = ValueAnimator.ofInt(minHeight, maxHeight)
        valueAnimator.duration = duration
        valueAnimator.addUpdateListener {
            val animatedValue = valueAnimator.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = animatedValue
            layoutParams.width = animatedValue
            view.layoutParams = layoutParams
        }
        valueAnimator.start()
    }

    private fun decreaseViewSize(view: View, duration: Long,maxHeight:Int,minHeight:Int) {
        val valueAnimator = ValueAnimator.ofInt(maxHeight, minHeight)
        valueAnimator.duration = duration
        valueAnimator.addUpdateListener {
            val animatedValue = valueAnimator.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = animatedValue
            layoutParams.width = animatedValue
            view.layoutParams = layoutParams
        }
        valueAnimator.start()
    }


}