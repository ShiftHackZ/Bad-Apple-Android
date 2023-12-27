@file:Suppress("UNCHECKED_CAST")

package com.shifthackz.badapple.ui.screen

import android.content.Context
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shifthackz.badapple.engine.Frame
import com.shifthackz.badapple.source.vector.BadAppleVectorFrames
import com.shifthackz.catppuccin.compose.CatppuccinTheme
import com.shifthackz.catppuccin.palette.Catppuccin

class BadAppleViewModelFactory(
    private val context: Context,
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val frames = BadAppleVectorFrames
            .get(context)
            .map { (id, resId) -> Frame.XmlRes(id, resId) }

        val descriptor = context.assets.openFd("music.mp3")

        val colors = listOf(
            Catppuccin.Latte.Rosewater.toArgb(),
            Catppuccin.Latte.Flamingo.toArgb(),
            Catppuccin.Latte.Pink.toArgb(),
            Catppuccin.Latte.Mauve.toArgb(),
            Catppuccin.Latte.Red.toArgb(),
            Catppuccin.Latte.Maroon.toArgb(),
            Catppuccin.Latte.Peach.toArgb(),
            Catppuccin.Latte.Yellow.toArgb(),
            Catppuccin.Latte.Green.toArgb(),
            Catppuccin.Latte.Teal.toArgb(),
            Catppuccin.Latte.Sky.toArgb(),
            Catppuccin.Latte.Sapphire.toArgb(),
            Catppuccin.Latte.Blue.toArgb(),
            Catppuccin.Latte.Lavender.toArgb(),
        )

        return BadAppleViewModel(frames, descriptor, colors) as T
    }
}
