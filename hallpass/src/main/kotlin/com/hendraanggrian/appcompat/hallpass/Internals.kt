@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.appcompat.hallpass

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

internal inline fun Array<out String>.asMap(): Map<String, Boolean> {
    val map = HashMap<String, Boolean>(size)
    forEach {
        map += it to true
    }
    return map
}

internal inline fun Array<out String>.asInput(): Array<String> {
    val input = Array(size) { "" }
    indices.forEach {
        input[it] = get(it)
    }
    return input
}

internal inline val Context.settingsIntent: Intent
    get() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.fromParts("package", packageName, null)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        return intent
    }
