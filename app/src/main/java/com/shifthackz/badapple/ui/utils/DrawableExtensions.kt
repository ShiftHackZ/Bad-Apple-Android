package com.shifthackz.badapple.ui.utils

import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.Drawable
import com.shifthackz.badapple.engine.Frame
import com.shifthackz.badapple.source.vector.BadAppleVectorFrames

fun Drawable?.replaceColor(oldColor: Int, newColor: Int): Drawable? {
    if (this == null) return null
    if (oldColor == newColor) return this

    val colorMatrix = ColorMatrix()

    val srcR = Color.red(oldColor)
    val srcG = Color.green(oldColor)
    val srcB = Color.blue(oldColor)

    val destR = Color.red(newColor)
    val destG = Color.green(newColor)
    val destB = Color.blue(newColor)

    colorMatrix.set(
        floatArrayOf(
            (destR - srcR) / 255f, 0f, 0f, 0f, srcR / 255f,
            0f, (destG - srcG) / 255f, 0f, 0f, srcG / 255f,
            0f, 0f, (destB - srcB) / 255f, 0f, srcB / 255f,
            0f, 0f, 0f, 1f, 0f
        )
    )

    val colorFilter = ColorMatrixColorFilter(colorMatrix)
    setColorFilter(colorFilter)

    return mutate()
}

val Frame.isLastOrEmpty: Boolean
    get() {
        if (this is Frame.Empty) return true
        return this is Frame.XmlRes && this.id >= BadAppleVectorFrames.MAX
    }