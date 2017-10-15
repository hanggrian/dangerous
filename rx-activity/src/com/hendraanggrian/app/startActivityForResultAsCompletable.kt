@file:JvmMultifileClass
@file:JvmName("RxActivitiesKt")
@file:Suppress("NOTHING_TO_INLINE", "UNUSED")

package com.hendraanggrian.app

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.annotation.RequiresApi
import io.reactivex.Completable

@JvmOverloads
inline fun Activity.startActivityForResultAsCompletable(
        intent: Intent,
        result: Int = Activity.RESULT_OK
): Completable = CompletableActivityResultEmitter.create(result, { requestCode ->
    startActivityForResult(intent, requestCode)
})

@JvmOverloads
@RequiresApi(16)
inline fun Activity.startActivityForResultAsCompletable(
        intent: Intent,
        result: Int = Activity.RESULT_OK,
        options: Bundle?
): Completable = CompletableActivityResultEmitter.create(result, { requestCode ->
    startActivityForResult(intent, requestCode, options)
})

@JvmOverloads
inline fun Fragment.startActivityForResultAsCompletable(
        intent: Intent,
        result: Int = Activity.RESULT_OK
): Completable = CompletableActivityResultEmitter.create(result, { requestCode ->
    startActivityForResult(intent, requestCode)
})

@JvmOverloads
@RequiresApi(16)
inline fun Fragment.startActivityForResultAsCompletable(
        intent: Intent,
        result: Int = Activity.RESULT_OK,
        options: Bundle?
): Completable = CompletableActivityResultEmitter.create(result, { requestCode ->
    startActivityForResult(intent, requestCode, options)
})

@JvmOverloads
inline fun android.support.v4.app.Fragment.startActivityForResultAsCompletable(
        intent: Intent,
        result: Int = Activity.RESULT_OK
): Completable = CompletableActivityResultEmitter.create(result, { requestCode ->
    startActivityForResult(intent, requestCode)
})

@JvmOverloads
@RequiresApi(16)
inline fun android.support.v4.app.Fragment.startActivityForResultAsCompletable(
        intent: Intent,
        result: Int = Activity.RESULT_OK,
        options: Bundle?
): Completable = CompletableActivityResultEmitter.create(result, { requestCode ->
    startActivityForResult(intent, requestCode, options)
})