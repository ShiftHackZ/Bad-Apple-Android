package com.shifthackz.badapple.engine.type

import android.graphics.Color
import android.os.SystemClock
import com.shifthackz.badapple.engine.ColorRenderer
import com.shifthackz.badapple.engine.Defaults
import com.shifthackz.badapple.engine.Engine
import com.shifthackz.badapple.engine.Loop
import com.shifthackz.badapple.engine.SequenceState

class ThemeEngine(
    private var renderer: ColorRenderer,
    private var fps: Int = Defaults.FPS,
): Engine, Loop {

    private var sequenceState: SequenceState = SequenceState.IDLE
    private var colors: List<Int> = emptyList()
    private var colorCurrent: Int = 0
    private var duration: Long = Defaults.DURATION

    init {
        require(fps > 0) {
            "fps must be higher than 0"
        }
    }

    fun load(
        colors: List<Int>,
        duration: Long = Defaults.DURATION,
        fps: Int = Defaults.FPS,
    ) {
        require(colors.isNotEmpty()) {
            "colors must not be empty"
        }
        require(fps > 0) {
            "fps must be higher than 0"
        }
        require(duration > 0) {
            "duration must be higher than 0"
        }
        this.colors = colors
        this.duration = duration
        this.fps = fps
    }

    fun setRenderer(renderer: ColorRenderer) {
        this.renderer = renderer
    }

    override fun pause() {
        this.sequenceState = SequenceState.IDLE
    }

    override fun play() {
        loop()
        this.sequenceState = SequenceState.RUNNING
    }

    override fun stop() {
        this.sequenceState = SequenceState.IDLE
    }

    override fun toggle() {
        if (sequenceState == SequenceState.RUNNING) pause()
        else play()
    }

    override fun loop() {
        if (sequenceState == SequenceState.RUNNING) return
        val thread = Thread {
            var animIterationRunning = false
            var startTime = SystemClock.uptimeMillis()
            while (true) {
                if (sequenceState != SequenceState.RUNNING) break
                if (!animIterationRunning) {
                    startTime = SystemClock.uptimeMillis()
                    animIterationRunning = true
                } else {
                    val (color1, color2) = getColors()
                    val elapsedTime = SystemClock.uptimeMillis() - startTime
                    if (elapsedTime < duration) {
                        val interpolation = elapsedTime.toFloat() / duration
                        val currentColor = interpolateColor(color1, color2, interpolation)
                        renderer.render(currentColor)
                    } else {
                        renderer.render(color2)
                        colorCurrent++
                        animIterationRunning = false
                    }
                }
                Thread.sleep(1000 / fps * 1L)
            }
        }
        thread.start()
    }

    private fun getColors(): Pair<Int, Int> {
        val color1 = colors.getOrNull(colorCurrent) ?: run {
            colorCurrent = 0
            colors[0]
        }
        val color2 = colors.getOrNull(colorCurrent + 1) ?: colors[0]
        return color1 to color2
    }

    private fun interpolateColor(color1: Int, color2: Int, interpolation: Float): Int {
        val r = (Color.red(color1) * (1 - interpolation) + Color.red(color2) * interpolation).toInt()
        val g = (Color.green(color1) * (1 - interpolation) + Color.green(color2) * interpolation).toInt()
        val b = (Color.blue(color1) * (1 - interpolation) + Color.blue(color2) * interpolation).toInt()
        return Color.rgb(r, g, b)
    }
}
