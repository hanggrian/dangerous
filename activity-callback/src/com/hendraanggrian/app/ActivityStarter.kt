package com.hendraanggrian.app

import android.content.Intent

interface ActivityStarter<T> {

    val resultCode: Int

    val block: T.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit

    val fallback: T.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit
}