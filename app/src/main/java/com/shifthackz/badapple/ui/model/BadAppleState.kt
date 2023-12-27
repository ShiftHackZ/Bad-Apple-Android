package com.shifthackz.badapple.ui.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.shifthackz.badapple.engine.Frame

@Immutable
data class BadAppleState(
    val frame: Frame = Frame.Empty,
    private val color: Int = Color.White.toArgb(),
    val mainButton: MainButtonState = MainButtonState.PLAY,
    val animateColor: Boolean = false,
) {
    val accentColor: Int
        get() {
            if (animateColor) return color
            return Color.White.toArgb()
        }
}
