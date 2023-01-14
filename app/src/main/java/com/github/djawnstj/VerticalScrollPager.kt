package com.github.djawnstj

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.widget.NestedScrollView
import kotlin.math.abs
import kotlin.math.absoluteValue

class VerticalScrollPager: NestedScrollView {

    companion object { private const val TAG = "CustomScrollView" }

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?): super(context!!, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int): super(context!!, attrs, defStyleAttr)

    private var isScrollable = true

    val gestureDetector = GestureDetector(context, object: GestureDetector.OnGestureListener {
        override fun onDown(p0: MotionEvent?): Boolean {
            return false
        }

        override fun onShowPress(p0: MotionEvent?) {
        }

        override fun onSingleTapUp(p0: MotionEvent?): Boolean {
            return false
        }

        override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
            p0?.y
            p1?.y

            return if(p0 != null && p1 != null ) {
                val startX = p0.x
                val endX = p1.x
                val startY = p0.y
                val endY = p1.y
                return !((startX - endX).absoluteValue > 10 && (startY - endY).absoluteValue < 10)
            } else {
                false
            }
        }

        override fun onLongPress(p0: MotionEvent?) {
        }

        override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
            return false
        }

    })

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {

        return if (isScrollable()) {
            super.onInterceptTouchEvent(ev)
        } else {
            return false
        }
    }

//    override fun onTouchEvent(ev: MotionEvent): Boolean {
//        return if (isScrollable()) {
//            super.onTouchEvent(ev)
//        } else {
//            false
//        }
//    }

    /**
     * Function that tells you if you are currently scrollable.
     * @return scrollable
     */
    fun isScrollable(): Boolean {
        return isScrollable
    }

    /**
     * Function to change scrollable.
     * @param scrollable scrollable status you want.
     */
    fun setScrollable(scrollable: Boolean) {
        this.isScrollable = scrollable
        this.isEnabled = scrollable
        this.invalidate()
    }



    /**
     * Obtain the absolute coordinates of the top of the this View.
     */
    private fun computeDistanceToView(view: View): Int {
        return abs(calculateRectOnScreen(this).top - (this.scrollY + calculateRectOnScreen(view).top))
    }

    /**
     * Find the absolute coordinates of the top of the view to be moved.
     */
    private fun calculateRectOnScreen(view: View): Rect {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        return Rect(
            location[0],
            location[1],
            location[0] + view.measuredWidth,
            location[1] + view.measuredHeight
        )
    }

    /**
     * Scroll to the top of the wanted view with animation.
     * @param view wanted view
     */
    fun scrollToView(view: View) {
        val y = computeDistanceToView(view)
        ObjectAnimator.ofInt(this, "scrollY", y).apply {
            duration = 500L     // 애니메이션 0.5초동안
        }.start()
    }

}