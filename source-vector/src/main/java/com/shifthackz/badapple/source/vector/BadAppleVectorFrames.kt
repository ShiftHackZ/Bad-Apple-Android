package com.shifthackz.badapple.source.vector

import android.annotation.SuppressLint
import android.content.Context

object BadAppleVectorFrames {
    const val MAX = 6572

    @SuppressLint("DiscouragedApi")
    fun get(context: Context): List<Pair<Int, Int>> {
        return (1..MAX)
            .map { id ->
                id to context
                    .resources
                    .getIdentifier("@drawable/bav_$id", null, context.packageName)
            }
    }
}
