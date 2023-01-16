package com.github.djawnstj

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.MeasureSpec.*
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.core.widget.NestedScrollView
import kotlin.math.abs
import kotlin.math.absoluteValue

public class VerticalScrollPager: NestedScrollView {

    public companion object { private const val TAG = "VerticalScrollPager" }

    public constructor(context: Context): super(context)
    public constructor(context: Context, attrs: AttributeSet?): super(context, attrs)
    public constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    private val topLayout by lazy { LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        layoutParams = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    } }

    private var childWidth = 100
    private var childHeight = 100

    init {
        /* add default top-level layout */
        addView(topLayout)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = getMode(widthMeasureSpec)
        val widthSize = getSize(widthMeasureSpec)
        val heightMode = getMode(heightMeasureSpec)
        val heightSize = getSize(heightMeasureSpec)

        //Measure Width
        val width: Int = when (widthMode) {
            //Must be this size
            EXACTLY -> widthSize
            //Can't be bigger than...
            AT_MOST -> Math.min(measuredWidth, widthSize)
            //Be whatever you want
            else -> measuredWidth
        }

        //Measure Height
        val height: Int = when (heightMode) {
            //Must be this size
            EXACTLY -> heightSize
            //Can't be bigger than...
            AT_MOST -> Math.min(measuredHeight, heightSize)
            //Be whatever you want
            else -> measuredHeight
        }

        /* Determine the size of the child view */
        childWidth = width - paddingLeft - paddingRight
        childHeight = height - paddingTop - paddingBottom

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        /* Force resize the view to be added.(match_parent)*/
        topLayout.let {
            it.children.forEach { child ->
                val lp = child.layoutParams
                lp.width = childWidth
                lp.height = childHeight
                child.layoutParams = lp
                child.requestLayout()
            }
        }
    }

    /**
     * Force resize the view to be added.(match_parent)
     */
    override fun addView(child: View) {
        if (child == topLayout) super.addView(child)
        else {
            val lp = LinearLayout.LayoutParams(childWidth, childHeight)
            child.layoutParams = lp
            topLayout.addView(child)
        }
    }

    /**
     * Force resize the view to be added.(match_parent)
     */
    override fun addView(child: View, width: Int, height: Int) {
        if (child == topLayout) super.addView(child, width, height)
        else topLayout.addView(child, childWidth, childHeight)
    }

    /**
     * Force resize the view to be added.(match_parent)
     */
    override fun addView(child: View, index: Int) {
        if (child == topLayout) super.addView(child, index)
        else {
            val lp = LinearLayout.LayoutParams(childWidth, childHeight)
            child.layoutParams = lp
            topLayout.addView(child, index)
        }
    }

    /**
     * Force resize the view to be added.(match_parent)
     */
    override fun addView(child: View, params: ViewGroup.LayoutParams) {
        if (child == topLayout) super.addView(child, params)
        else {
            params.width = childWidth
            params.height = childHeight
            topLayout.addView(child, params)
        }
    }

    /**
     * Force resize the view to be added.(match_parent)
     */
    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        if (child == topLayout) super.addView(child, index, params)
        else {
            params.width = childWidth
            params.height = childHeight
            topLayout.addView(child, index, params)
        }
    }

    private var isScrollable = true

    public val gestureDetector: GestureDetector = GestureDetector(context, object: GestureDetector.OnGestureListener {
        override fun onDown(p0: MotionEvent?): Boolean {
            return false
        }

        override fun onShowPress(p0: MotionEvent?) {
        }

        override fun onSingleTapUp(p0: MotionEvent?): Boolean {
            return false
        }

        override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
            return if(p0 != null && p1 != null ) {
                val startX = p0.x
                val endX = p1.x
                val startY = p0.y
                val endY = p1.y
                return ((startX - endX).absoluteValue > 10 && (startY - endY).absoluteValue < 10)
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

    /**
     * Function that tells you if you are currently scrollable.
     * @return scrollable
     */
    public fun isScrollable(): Boolean {
        return isScrollable
    }

    /**
     * Function to change scrollable.
     * @param scrollable scrollable status you want.
     */
    public fun setScrollable(scrollable: Boolean) {
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
    public fun scrollToView(view: View) {
        val y = computeDistanceToView(view)
        ObjectAnimator.ofInt(this, "scrollY", y).apply {
            duration = 500L     // 애니메이션 0.5초동안
        }.start()
    }

}