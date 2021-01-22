package ai.kaira.app.Utils

import ai.kaira.app.R
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.util.DisplayMetrics
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView


class SlidingDotAnimator constructor(val context: Context, val loadingDotViewOne: ImageView,
                                     val loadingDotViewTwo: ImageView,
                                     val loadingDotViewThree: ImageView) {

    fun changeLoadingDot(currentOnboardingPageNum: Int, swipeType: SwipeDetector.SwipeTypeEnum?){

        var slideAnimator = ValueAnimator
                .ofInt(dpToPx( 50), dpToPx(25))
                .setDuration(400)

        var slideAnimator2 = ValueAnimator
                .ofInt(dpToPx(25), dpToPx(50))
                .setDuration(400)

        if(currentOnboardingPageNum == 2 && swipeType == SwipeDetector.SwipeTypeEnum.RIGHT_TO_LEFT){
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
        }else if(currentOnboardingPageNum == 3 && swipeType == SwipeDetector.SwipeTypeEnum.RIGHT_TO_LEFT){
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
        if(currentOnboardingPageNum == 1 && swipeType == SwipeDetector.SwipeTypeEnum.LEFT_TO_RIGHT){
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
        }else if(currentOnboardingPageNum == 2 && swipeType == SwipeDetector.SwipeTypeEnum.LEFT_TO_RIGHT){
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
        var animationSet = AnimatorSet();
        animationSet.setInterpolator(AccelerateDecelerateInterpolator());
        animationSet.play(slideAnimator).with(slideAnimator2)
        animationSet.start()
    }

    fun dpToPx(dp: Int): Int {
        val displayMetrics: DisplayMetrics = context.getResources().getDisplayMetrics()
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }
}