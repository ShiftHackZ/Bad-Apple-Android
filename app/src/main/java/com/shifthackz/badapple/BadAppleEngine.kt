package com.shifthackz.badapple

import android.content.res.AssetFileDescriptor
import com.shifthackz.badapple.engine.Frame
import com.shifthackz.badapple.engine.Engine
import com.shifthackz.badapple.engine.type.FrameEngine
import com.shifthackz.badapple.engine.type.MultilayerEngine
import com.shifthackz.badapple.engine.type.MusicEngine
import com.shifthackz.badapple.engine.type.ThemeEngine
import com.shifthackz.badapple.engine.ColorRenderer
import com.shifthackz.badapple.engine.FrameRenderer

class BadAppleEngine(
    colorRenderer: ColorRenderer,
    frameRenderer: FrameRenderer,
    colors: List<Int>,
    frames: List<Frame>,
    audio: AssetFileDescriptor,
) : Engine {

    private val musicEngine = MusicEngine()
    private val frameEngine = FrameEngine(frameRenderer)
    private val themeEngine = ThemeEngine(colorRenderer)

    private val multilayer = MultilayerEngine(setOf(musicEngine, frameEngine, themeEngine))

    init {
        musicEngine.load(audio)
        frameEngine.load(frames)
        themeEngine.load(colors)
    }

    override fun pause() = multilayer.pause()
    override fun play() = multilayer.play()
    override fun stop() = multilayer.stop()
    override fun toggle() = multilayer.toggle()
}
