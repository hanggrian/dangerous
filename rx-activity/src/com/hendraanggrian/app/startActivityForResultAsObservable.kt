@file:JvmMultifileClass
@file:JvmName("RxActivitiesKt")
@file:Suppress("NOTHING_TO_INLINE", "UNUSED")

package com.hendraanggrian.app

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.annotation.RequiresApi
import io.reactivex.Observable

@JvmOverloads
inline fun Activity.startActivityForResultAsObservable(
        intent: Intent,
        result: Int = Activity.RESULT_OK
): Observable<Intent> = ObservableActivityResultEmitter.create(result, { requestCode ->
    startActivityForResult(intent, requestCode)
})

@JvmOverloads
@RequiresApi(16)
inline fun Activity.startActivityForResultAsObservable(
        intent: Intent,
        result: Int = Activity.RESULT_OK,
        options: Bundle?
): Observable<Intent> = ObservableActivityResultEmitter.create(result, { requestCode ->
    startActivityForResult(intent, requestCode, options)
})

@JvmOverloads
inline fun Fragment.startActivityForResultAsObservable(
        intent: Intent,
        result: Int = Activity.RESULT_OK
): Observable<Intent> = ObservableActivityResultEmitter.create(result, { requestCode ->
    startActivityForResult(intent, requestCode)
})

@JvmOverloads
@RequiresApi(16)
inline fun Fragment.startActivityForResultAsObservable(
        intent: Intent,
        result: Int = Activity.RESULT_OK,
        options: Bundle?
): Observable<Intent> = ObservableActivityResultEmitter.create(result, { requestCode ->
    startActivityForResult(intent, requestCode, options)
})

@JvmOverloads
inline fun android.support.v4.app.Fragment.startActivityForResultAsObservable(
        intent: Intent,
        result: Int = Activity.RESULT_OK
): Observable<Intent> = ObservableActivityResultEmitter.create(result, { requestCode ->
    startActivityForResult(intent, requestCode)
})

@JvmOverloads
@RequiresApi(16)
inline fun android.support.v4.app.Fragment.startActivityForResultAsObservable(
        intent: Intent,
        result: Int = Activity.RESULT_OK,
        options: Bundle?
): Observable<Intent> = ObservableActivityResultEmitter.create(result, { requestCode ->
    startActivityForResult(intent, requestCode, options)
})