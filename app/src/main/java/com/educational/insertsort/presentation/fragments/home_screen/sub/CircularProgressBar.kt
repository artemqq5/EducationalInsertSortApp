package com.educational.insertsort.presentation.fragments.home_screen.sub

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.educational.insertsort.R
import kotlin.math.min

class CircularProgressBar(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val circlePaint = Paint().apply {
        color = context.getColor(com.google.android.material.R.color.material_blue_grey_800)
        style = Paint.Style.STROKE
        strokeWidth = 30f
    }

    private val progressPaint = Paint().apply {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(
            com.google.android.material.R.attr.colorPrimary,
            typedValue,
            true
        )
        color = typedValue.data
        style = Paint.Style.STROKE
        strokeWidth = 25f
    }

    private val textPaint = Paint().apply {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(
            com.google.android.material.R.attr.colorOnSurface,
            typedValue,
            true
        )
        typeface = ResourcesCompat.getFont(context, R.font.pitagon_light)
        color = typedValue.data
        textSize = 35f
        textAlign = Paint.Align.CENTER
    }

    var progress: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()

        val radius = min(width, height) / 2 - circlePaint.strokeWidth
        val centerX = width / 2
        val centerY = height / 2

        // Draw circle
        canvas.drawCircle(centerX, centerY, radius, circlePaint)

        // Draw text
        val text = "$progress%"
        canvas.drawText(text, centerX, centerY + textPaint.textSize / 3, textPaint)

        // Draw arc to represent progress
        val sweepAngle = (progress / 100) * 360
        canvas.drawArc(
            centerX - radius, centerY - radius,
            centerX + radius, centerY + radius,
            -90f, sweepAngle, false, progressPaint
        )
    }
}
