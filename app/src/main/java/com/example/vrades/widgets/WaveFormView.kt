package com.example.vrades.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.vrades.R

@SuppressLint("ResourceAsColor")
class WaveFormView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var paint = Paint()
    private var amplitudes = ArrayList<Float>()
    private var spikes = ArrayList<RectF>()
    private var distance = 6f
    private var radius = 6f
    private var width = 9f
    private var screenWidth = 0f
    private var screenHeight = 400f
    private var maxSpikes = 0

    init {
        paint.color = R.color.orange
        screenWidth = resources.displayMetrics.widthPixels.toFloat()
        maxSpikes = (screenWidth / (width + distance)).toInt()
    }

    fun addAmplitude(amp: Float) {
        amplitudes.add(amp)
        spikes.clear()
        val amps = amplitudes.takeLast(maxSpikes)
        amps.indices.forEach {
            val left = screenWidth - it * (width + distance)
            val top = screenHeight / 2 - amps[it] / 2
            val right = left + width
            val bottom = top + height
            spikes.add(RectF(left, top, right, bottom))
        }
        invalidate() //Triggers the function call with draw()
    }
    fun clear(): ArrayList<*> {
        val amps = amplitudes.clone() as ArrayList<*>
        amplitudes.clear()
        spikes.clear()
        invalidate()
        return amps
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        spikes.forEach {
            canvas?.drawRoundRect(it, radius, radius, paint)
        }
    }
}