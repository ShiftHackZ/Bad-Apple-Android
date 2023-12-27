package com.shifthackz.badapple.ui.screen

import android.content.res.AssetFileDescriptor
import androidx.lifecycle.ViewModel
import com.shifthackz.badapple.BadAppleEngine
import com.shifthackz.badapple.engine.ColorRenderer
import com.shifthackz.badapple.engine.Frame
import com.shifthackz.badapple.engine.Engine
import com.shifthackz.badapple.engine.FrameRenderer
import com.shifthackz.badapple.ui.contract.ActionListener
import com.shifthackz.badapple.ui.model.BadAppleState
import com.shifthackz.badapple.ui.model.MainButtonState
import com.shifthackz.badapple.ui.utils.isLastOrEmpty
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BadAppleViewModel(
    frames: List<Frame>,
    descriptor: AssetFileDescriptor,
    colors: List<Int>,
) : ViewModel(), ActionListener {

    private val frameRenderer = FrameRenderer { frame ->
        _uiState.update { ui -> ui.copy(frame = frame) }
        if (frame.isLastOrEmpty) stopPlayback()
    }

    private val colorRenderer = ColorRenderer { color ->
        _uiState.update { ui -> ui.copy(color = color) }
    }

    private val engine: Engine = BadAppleEngine(
        colorRenderer = colorRenderer,
        frameRenderer = frameRenderer,
        colors = colors,
        frames = frames,
        audio = descriptor,
    )

    private val _uiState = MutableStateFlow(BadAppleState())
    val uiState: StateFlow<BadAppleState> = _uiState.asStateFlow()

    override fun togglePlayback() {
        engine.toggle()
        _uiState.update { ui -> ui.copy(mainButton = ui.mainButton.toggle()) }
    }

    override fun pausePlayback() {
        engine.pause()
        _uiState.update { ui -> ui.copy(mainButton = MainButtonState.PLAY) }
    }

    override fun stopPlayback() {
        engine.stop()
        _uiState.update { ui -> ui.copy(mainButton = MainButtonState.PLAY) }
    }

    override fun setAnimateColor(value: Boolean) {
        _uiState.update { ui -> ui.copy(animateColor = value) }
    }
}
