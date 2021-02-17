package ai.kaira.app.onboarding

import ai.kaira.app.R
import ai.kaira.app.termsconditions.TermsConditionActivity
import ai.kaira.app.utils.SwipeDetector
import ai.kaira.app.databinding.ActivityOnboardingBinding
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil


class OnboardActivity : AppCompatActivity() {

    lateinit var onboardingBinding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onboardingBinding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding)

        val onboardSlidingDotAnimator = OnboardSlidingDotAnimator(applicationContext,onboardingBinding.loadingDotOne,onboardingBinding.loadingDotTwo,onboardingBinding.loadingDotThree)
        initializeSwipeDetector(onboardSlidingDotAnimator)

        onboardingBinding?.skipBtn?.setOnClickListener {
            finish()
            startActivity(Intent(this,TermsConditionActivity::class.java))
        }

        onboardingBinding?.loginBtn?.setOnClickListener {
            //TODO start Login Activity
        }
    }

    private fun initializeSwipeDetector(onboardSlidingDotAnimator : OnboardSlidingDotAnimator){
        SwipeDetector(onboardingBinding.root).setOnSwipeListener(object : SwipeDetector.onSwipeEvent {
            override fun onSwipeDetected(v: View?, swipeType: SwipeDetector.SwipeTypeEnum?, pageNum : Int) {
                if(SwipeDetector.SwipeTypeEnum.RIGHT_TO_LEFT == swipeType){
                    loadPage(pageNum)
                    onboardSlidingDotAnimator.changeLoadingDot(pageNum, SwipeDetector.SwipeTypeEnum.RIGHT_TO_LEFT)
                }else if(SwipeDetector.SwipeTypeEnum.LEFT_TO_RIGHT == swipeType){
                    loadPage(pageNum)
                    onboardSlidingDotAnimator.changeLoadingDot(pageNum, SwipeDetector.SwipeTypeEnum.LEFT_TO_RIGHT)
                }
            }

            override fun onStartActivity() {
                finish()
                startActivity(Intent(this@OnboardActivity,TermsConditionActivity::class.java))
            }
        })
    }
    fun loadPage(onboardingPageNum : Int){
        var  onboardTitle = 0
        var  onboardDescription = 0
        var  onboardDrawableImage = 0
        when(onboardingPageNum){
            1 -> {
                onboardTitle = R.string.onboarding_title_1
                onboardDescription = R.string.onboarding_description_1
                onboardDrawableImage = R.drawable.onboarding_image_one
            }
            2 -> {
                onboardTitle = R.string.onboarding_title_2
                onboardDescription = R.string.onboarding_description_2
                onboardDrawableImage = R.drawable.onboarding_image_two
            }
            3 -> {
                onboardTitle = R.string.onboarding_title_3
                onboardDescription = R.string.onboarding_description_3
                onboardDrawableImage = R.drawable.onboarding_image_three
            }
        }

        onboardingBinding.onboardingTitle.setText(getString(onboardTitle))
        onboardingBinding.onboardingDescription.setText(getString(onboardDescription))
        onboardDrawableImage?.let {
            onboardingBinding.onboardingImage.setImageDrawable(getDrawable(onboardDrawableImage))
        }
    }



}