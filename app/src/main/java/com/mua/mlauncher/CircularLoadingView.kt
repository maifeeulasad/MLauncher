package com.mua.mlauncher

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.math.min

class CircularLoadingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var circlePaint: Paint = Paint().apply {
        color = ContextCompat.getColor(context, android.R.color.darker_gray)  // Default circle color
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }

    private var angle = 0f
    private var startAngle = 0f
    private val boundingRect = RectF()

    init {
        // Here, you can fetch custom attributes if you define them in XML.
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = min(measuredWidth, measuredHeight)
        setMeasuredDimension(size, size)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val inset = circlePaint.strokeWidth / 2
        boundingRect.set(inset, inset, w - inset, h - inset)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawArc(
            boundingRect,
            startAngle,
            angle,
            false,
            circlePaint
        )

        startAngle += 5
        if (startAngle > 360) startAngle = 0f
        angle += 5
        if (angle > 360) angle = 0f

        // Invalidate for continuous rotation
        invalidate()
    }
}
