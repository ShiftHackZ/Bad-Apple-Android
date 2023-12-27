package com.shifthackz.badapple.engine

import androidx.annotation.DrawableRes

sealed interface Frame {
    data object Empty : Frame
    data class XmlRes(val id: Int, @DrawableRes val resId: Int) : Frame
}
