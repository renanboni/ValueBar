package com.boni.widget

import android.content.Context
import android.graphics.*
import android.graphics.Paint.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import com.boni.valuebar.R
import android.R.attr.isIndicator



class ValueBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    private var maxValue = 100
    private var currentValue = 10

    // Dimensions
    private var barHeight: Int = 20
    private var circleRadius: Int = 0

    // Colors
    private var baseColor: Int = 0
    private var circleColor: Int = 0

    private var barBasePaint: Paint
    private var circlePaint: Paint

    //
    private var currentPosition: Float = 0f

    private var circleRect: Rect

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.ValueBar, 0, 0)

        with(typedArray) {
            barHeight = getDimensionPixelSize(R.styleable.ValueBar_barHeight, 20)
            baseColor = getColor(R.styleable.ValueBar_baseColor, Color.BLACK)
            circleColor = getColor(R.styleable.ValueBar_circleColor, Color.BLACK)
            circleRadius = getDimensionPixelSize(R.styleable.ValueBar_circleRadius, 20)
        }

        typedArray.recycle()

        barBasePaint = Paint(ANTI_ALIAS_FLAG).also {
            it.color = baseColor
        }

        circlePaint = Paint(ANTI_ALIAS_FLAG).also {
            it.color = circleColor
        }

        circleRect = Rect(currentPosition.toInt(), 0, circleRadius * 2, circleRadius * 2)

        setOnTouchListener { _ , event ->
            val x = event.x
            val y = event.y

            when(event.action) {
                ACTION_DOWN -> {
                    if(circleRect.contains(x.toInt(), y.toInt())) {
                        currentPosition = x
                        invalidate()
                    }
                }
                ACTION_UP -> {
                    currentPosition = x
                    invalidate()
                }
                ACTION_MOVE -> {
                    currentPosition = x
                    invalidate()
                }
            }

            true
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawBar(canvas)
        drawCircle(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)


    }

    private fun drawBar(canvas: Canvas) {
        val barWidth = width - paddingLeft - paddingRight

        val barCenter = getBarCenter()

        val halfBarHeight = barCenter * .5f

        val top = barCenter - halfBarHeight
        val bottom = barCenter + halfBarHeight
        val left = paddingLeft
        val right = barWidth + paddingRight

        val rectF = RectF(left.toFloat(), top, right.toFloat(), bottom)
        canvas.drawRoundRect(rectF, halfBarHeight, halfBarHeight, barBasePaint)
    }

    private fun drawCircle(canvas: Canvas) {
        circleRect.left = currentPosition.toInt()
        canvas.drawCircle(currentPosition, getBarCenter(), (circleRadius * 2).toFloat(), circlePaint)
    }

    private fun getBarCenter() = (barHeight - paddingTop - paddingBottom) * .5f


}