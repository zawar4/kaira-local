package ai.kaira.app.Utils

import ai.kaira.app.R
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import com.google.android.material.internal.ViewUtils
import dagger.hilt.android.qualifiers.ApplicationContext

class LoadingDotAnimator constructor(var context: Context) {
    @SuppressLint("RestrictedApi")
    fun changeLoadingDot(currentOnboardingPageNum : Int, previousOnboardingPageNum : Int, loadingDotViewOne: ImageView,
                         loadingDotViewTwo: ImageView,
                         loadingDotViewThree: ImageView){
        var slideAnimator = ValueAnimator
                .ofInt(ViewUtils.dpToPx(context, 50).toInt(), ViewUtils.dpToPx(context, 25).toInt())
                .setDuration(400)

        var slideAnimator2 = ValueAnimator
                .ofInt(ViewUtils.dpToPx(context, 25).toInt(), ViewUtils.dpToPx(context, 50).toInt())
                .setDuration(400)
        if(currentOnboardingPageNum == 1 && previousOnboardingPageNum == 2){
            loadingDotViewOne.setImageDrawable(context.getDrawable(R.drawable.rectangle_filled))
            loadingDotViewTwo.setImageDrawable(context.getDrawable(R.drawable.rectangle_empty))
            slideAnimator.addUpdateListener { animation1: ValueAnimator ->
                val value = animation1.animatedValue as Int
                loadingDotViewOne.getLayoutParams().width = value
                loadingDotViewOne.requestLayout()

            }
            slideAnimator2.addUpdateListener { animation1: ValueAnimator ->
                val value = animation1.animatedValue as Int
                loadingDotViewTwo.getLayoutParams().width = value
                loadingDotViewTwo.requestLayout()

            }
        }else if(currentOnboardingPageNum == 2 && previousOnboardingPageNum == 3){
            loadingDotViewTwo.setImageDrawable(context.getDrawable(R.drawable.rectangle_filled))
            loadingDotViewThree.setImageDrawable(context.getDrawable(R.drawable.rectangle_empty))
            slideAnimator.addUpdateListener { animation1: ValueAnimator ->
                val value = animation1.animatedValue as Int
                loadingDotViewTwo.getLayoutParams().width = value
                loadingDotViewTwo.requestLayout()

            }
            slideAnimator2.addUpdateListener { animation1: ValueAnimator ->
                val value = animation1.animatedValue as Int
                loadingDotViewThree.getLayoutParams().width = value
                loadingDotViewThree.requestLayout()

            }
        }
        if(currentOnboardingPageNum == 2 && previousOnboardingPageNum == 1){
            loadingDotViewTwo.setImageDrawable(context.getDrawable(R.drawable.rectangle_filled))
            loadingDotViewOne.setImageDrawable(context.getDrawable(R.drawable.rectangle_empty))
            slideAnimator.addUpdateListener { animation1: ValueAnimator ->
                val value = animation1.animatedValue as Int
                loadingDotViewTwo.getLayoutParams().width = value
                loadingDotViewTwo.requestLayout()

            }
            slideAnimator2.addUpdateListener { animation1: ValueAnimator ->
                val value = animation1.animatedValue as Int
                loadingDotViewOne.getLayoutParams().width = value
                loadingDotViewOne.requestLayout()

            }
        }else if(currentOnboardingPageNum == 3 && previousOnboardingPageNum == 2){
            loadingDotViewThree.setImageDrawable(context.getDrawable(R.drawable.rectangle_filled))
            loadingDotViewTwo.setImageDrawable(context.getDrawable(R.drawable.rectangle_empty))
            slideAnimator.addUpdateListener { animation1: ValueAnimator ->
                val value = animation1.animatedValue as Int
                loadingDotViewThree.getLayoutParams().width = value
                loadingDotViewThree.requestLayout()

            }
            slideAnimator2.addUpdateListener { animation1: ValueAnimator ->
                val value = animation1.animatedValue as Int
                loadingDotViewTwo.getLayoutParams().width = value
                loadingDotViewTwo.requestLayout()
            }
        }
        var animationSet = AnimatorSet();
        animationSet.setInterpolator(AccelerateDecelerateInterpolator());
        animationSet.play(slideAnimator).with(slideAnimator2)
        animationSet.start()
    }
}