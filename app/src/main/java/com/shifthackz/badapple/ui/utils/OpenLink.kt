package com.shifthackz.badapple.ui.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openWebPage(url: String?) {
    try {
        val webpage = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
