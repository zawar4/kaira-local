package ai.kaira.app.Onboarding

import ai.kaira.app.R
import ai.kaira.app.Utils.LoadingDotAnimator
import ai.kaira.app.Utils.SwipeDetector
import ai.kaira.app.databinding.ActivityOnboardingBinding
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil


class OnboardActivity : AppCompatActivity() {

    lateinit var onboardingLayoutBinding: ActivityOnboardingBinding
    var currentPage = 1
    val loadingDotAnimator : LoadingDotAnimator by lazy {
        LoadingDotAnimator(applicationContext)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onboardingLayoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding)

        SwipeDetector(onboardingLayoutBinding.root).setOnSwipeListener(object : SwipeDetector.onSwipeEvent {
            override fun SwipeEventDetected(v: View?, swipeType: SwipeDetector.SwipeTypeEnum?) {
                if(SwipeDetector.SwipeTypeEnum.RIGHT_TO_LEFT == swipeType){
                    if(currentPage == 1){
                        currentPage++
                        loadPage(currentPage)
                        loadingDotAnimator.changeLoadingDot(currentPage,1,onboardingLayoutBinding.loadingDotOne,onboardingLayoutBinding.loadingDotTwo,onboardingLayoutBinding.loadingDotThree)
                    }else if(currentPage == 2){
                        currentPage++
                        loadPage(currentPage)
                        loadingDotAnimator.changeLoadingDot(currentPage,2,onboardingLayoutBinding.loadingDotOne,onboardingLayoutBinding.loadingDotTwo,onboardingLayoutBinding.loadingDotThree)
                    }
                }else if(SwipeDetector.SwipeTypeEnum.LEFT_TO_RIGHT == swipeType){
                    if(currentPage == 2){
                        currentPage--
                        loadPage(currentPage)
                        loadingDotAnimator.changeLoadingDot(currentPage,2,onboardingLayoutBinding.loadingDotOne,onboardingLayoutBinding.loadingDotTwo,onboardingLayoutBinding.loadingDotThree)
                    }else if(currentPage == 3){
                        currentPage--
                        loadPage(currentPage)
                        loadingDotAnimator.changeLoadingDot(currentPage,3,onboardingLayoutBinding.loadingDotOne,onboardingLayoutBinding.loadingDotTwo,onboardingLayoutBinding.loadingDotThree)
                    }
                }
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
        onboardingLayoutBinding.onboardingTitle.setText(getString(onboardTitle))
        onboardingLayoutBinding.onboardingDescription.setText(getString(onboardDescription))
        onboardDrawableImage?.let {
            onboardingLayoutBinding.onboardingImage.setImageDrawable(getDrawable(onboardDrawableImage))
        }
    }



}