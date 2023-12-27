@file:Suppress("unused")

package com.shifthackz.badapple.engine.type

import com.shifthackz.badapple.engine.Defaults
import com.shifthackz.badapple.engine.Engine
import com.shifthackz.badapple.engine.Frame
import com.shifthackz.badapple.engine.FrameRenderer
import com.shifthackz.badapple.engine.Loop
import com.shifthackz.badapple.engine.SequenceState
import com.shifthackz.badapple.engine.renderer.NoopFrameRenderer

class FrameEngine(
    private var frameRenderer: FrameRenderer = NoopFrameRenderer(),
    private var fps: Int = Defaults.FPS,
) : Engine, Loop {

    private var sequenceState: SequenceState = SequenceState.IDLE
    private var frames: List<Frame> = emptyList()
    private var frameCurrent: Int = 0

    init {
        require(fps > 0) {
            "fps must be higher than 0"
        }
    }

    fun load(
        frames: List<Frame>,
        fps: Int = Defaults.FPS,
    ) {
        require(frames.isNotEmpty()) {
            "frames must not be empty"
        }
        require(fps > 0) {
            "fps must be higher than 0"
        }
        this.sequenceState = SequenceState.IDLE
        this.frames = frames
        this.frameCurrent = 0
        this.fps = fps
    }

    fun setRenderer(frameRenderer: FrameRenderer) {
        this.frameRenderer = frameRenderer
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
        this.frameCurrent = 0
        frameRenderer.render(frames[0])
    }

    override fun toggle() {
        if (sequenceState == SequenceState.RUNNING) pause()
        else play()
    }

    override fun loop() {
        if (sequenceState == SequenceState.RUNNING) return
        val thread = Thread {
            while (true) {
                if (frameCurrent > frames.size - 1 || sequenceState != SequenceState.RUNNING) {
                    break
                }
                frameRenderer.render(frames[frameCurrent])
                frameCurrent++
                Thread.sleep(1_000 / fps * 1L)
            }
        }
        thread.start()
    }
}
