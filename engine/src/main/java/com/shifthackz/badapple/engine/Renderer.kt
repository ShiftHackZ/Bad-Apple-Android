package com.shifthackz.badapple.engine

typealias FrameRenderer = Renderer<Frame>
typealias ColorRenderer = Renderer<Int>

fun interface Renderer<T> {
    fun render(data: T)
}
