package com.shifthackz.badapple.ui.contract

interface ActionListener {
    fun togglePlayback()
    fun pausePlayback()
    fun stopPlayback()
    fun setAnimateColor(value: Boolean)
}
