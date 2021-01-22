package ai.kaira.app.Utils

import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener

class SwipeDetector(private val v: View) : OnTouchListener {
    private var min_distance = 100
    private var downX = 0f
    private var downY = 0f
    private var upX = 0f
    private var upY = 0f
    private var swipeEventListener: onSwipeEvent? = null
    fun setOnSwipeListener(listener: onSwipeEvent?) {
        try {
            swipeEventListener = listener
        } catch (e: ClassCastException) {
            Log.e("ClassCastException", "please pass ai.kaira.app.Utils.SwipeDetector.onSwipeEvent Interface instance", e)
        }
    }

    fun onRightToLeftSwipe() {
        if (swipeEventListener != null) swipeEventListener!!.SwipeEventDetected(v, SwipeTypeEnum.RIGHT_TO_LEFT) else Log.e("ai.kaira.app.Utils.SwipeDetector error", "please pass ai.kaira.app.Utils.SwipeDetector.onSwipeEvent Interface instance")
    }

    fun onLeftToRightSwipe() {
        if (swipeEventListener != null) swipeEventListener!!.SwipeEventDetected(v, SwipeTypeEnum.LEFT_TO_RIGHT) else Log.e("ai.kaira.app.Utils.SwipeDetector error", "please pass ai.kaira.app.Utils.SwipeDetector.onSwipeEvent Interface instance")
    }

    fun onTopToBottomSwipe() {
        if (swipeEventListener != null) swipeEventListener!!.SwipeEventDetected(v, SwipeTypeEnum.TOP_TO_BOTTOM) else Log.e("ai.kaira.app.Utils.SwipeDetector error", "please pass ai.kaira.app.Utils.SwipeDetector.onSwipeEvent Interface instance")
    }

    fun onBottomToTopSwipe() {
        if (swipeEventListener != null) swipeEventListener!!.SwipeEventDetected(v, SwipeTypeEnum.BOTTOM_TO_TOP) else Log.e("ai.kaira.app.Utils.SwipeDetector error", "please pass ai.kaira.app.Utils.SwipeDetector.onSwipeEvent Interface instance")
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                return true
            }
            MotionEvent.ACTION_UP -> {
                upX = event.x
                upY = event.y
                val deltaX = downX - upX
                val deltaY = downY - upY

                //HORIZONTAL SCROLL
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    if (Math.abs(deltaX) > min_distance) {
                        // left or right
                        if (deltaX < 0) {
                            onLeftToRightSwipe()
                            return true
                        }
                        if (deltaX > 0) {
                            onRightToLeftSwipe()
                            return true
                        }
                    } else {
                        //not long enough swipe...
                        return false
                    }
                } else {
                    if (Math.abs(deltaY) > min_distance) {
                        // top or down
                        if (deltaY < 0) {
                            onTopToBottomSwipe()
                            return true
                        }
                        if (deltaY > 0) {
                            onBottomToTopSwipe()
                            return true
                        }
                    } else {
                        //not long enough swipe...
                        return false
                    }
                }
                return true
            }
        }
        return false
    }

    interface onSwipeEvent {
        fun SwipeEventDetected(v: View?, SwipeType: SwipeTypeEnum?)
    }

    fun setMinDistanceInPixels(min_distance: Int): SwipeDetector {
        this.min_distance = min_distance
        return this
    }

    enum class SwipeTypeEnum {
        RIGHT_TO_LEFT, LEFT_TO_RIGHT, TOP_TO_BOTTOM, BOTTOM_TO_TOP
    }

    init {
        v.setOnTouchListener(this)
    }
}