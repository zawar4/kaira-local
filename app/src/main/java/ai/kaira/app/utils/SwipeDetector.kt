package ai.kaira.app.utils

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
    private var pageNum :Int = 1
    private var maxPages : Int = 3
    fun setOnSwipeListener(listener: onSwipeEvent?) {
        try {
            swipeEventListener = listener
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    fun onRightToLeftSwipe() {
        if(pageNum < maxPages){
            pageNum++;
            swipeEventListener?.onSwipeDetected(v, SwipeTypeEnum.RIGHT_TO_LEFT,pageNum)
        }else{
            swipeEventListener?.onStartActivity()
        }

    }

    fun onLeftToRightSwipe() {
        if(pageNum > 1){
            pageNum--;
            swipeEventListener?.onSwipeDetected(v, SwipeTypeEnum.LEFT_TO_RIGHT,pageNum)
        }

    }

    fun onTopToBottomSwipe() {
        swipeEventListener?.onSwipeDetected(v, SwipeTypeEnum.TOP_TO_BOTTOM,pageNum)
    }

    fun onBottomToTopSwipe() {
        swipeEventListener?.onSwipeDetected(v, SwipeTypeEnum.BOTTOM_TO_TOP,pageNum)
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
        fun onSwipeDetected(v: View?, SwipeType: SwipeTypeEnum?,currentPageNum : Int)
        fun onStartActivity()
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