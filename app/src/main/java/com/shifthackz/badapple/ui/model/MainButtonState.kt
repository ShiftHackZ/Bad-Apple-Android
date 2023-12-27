package com.shifthackz.badapple.ui.model

enum class MainButtonState {
    PLAY,
    PAUSE;

    fun toggle(): MainButtonState {
        if (this == PLAY) return PAUSE
        return PLAY
    }
}
