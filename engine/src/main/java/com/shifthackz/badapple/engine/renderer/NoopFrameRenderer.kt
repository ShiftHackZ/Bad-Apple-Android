package com.shifthackz.badapple.engine.renderer

import com.shifthackz.badapple.engine.Frame
import com.shifthackz.badapple.engine.FrameRenderer

class NoopFrameRenderer : FrameRenderer {
    override fun render(data: Frame) = Unit
}
