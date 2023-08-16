package com.hendraanggrian.appcompat.dangerous

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

/** [androidx.activity.result.ActivityResultLauncher.launch] only takes `Array<String>`. */
internal inline fun Array<out String>.duplicate(): Array<String> =
    copyInto(Array(size) { "" })

/** Intent to manage the caller's app in the Settings app. */
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
