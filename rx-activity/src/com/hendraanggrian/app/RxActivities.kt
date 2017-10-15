@file:JvmName("RxActivitiesKt")
@file:Suppress("NOTHING_TO_INLINE", "UNUSED")

package com.hendraanggrian.app

import android.annotation.TargetApi
import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.annotation.RequiresApi
import com.hendraanggrian.app.RxActivity.createActivityResultObservables
import io.reactivex.Observable

@JvmOverloads
inline fun Activity.startActivityForResultAsObservable(
        intent: Intent,
        result: Int = Activity.RESULT_OK
): Observable<Intent> {
    return createActivityResultObservables(result, { requestCode ->
        startActivityForResult(intent, requestCode)
    })
}

@RequiresApi(16)
@TargetApi(16)
@JvmOverloads
inline fun Activity.startActivityForResultAsObservable(
        intent: Intent,
        options: Bundle?,
        result: Int = Activity.RESULT_OK
): Observable<Intent> {
    return createActivityResultObservables(result, { requestCode ->
        startActivityForResult(intent, requestCode, options)
    })
}

@JvmOverloads
inline fun Fragment.startActivityForResultAsObservable(
        intent: Intent,
        result: Int = Activity.RESULT_OK
): Observable<Intent> {
    return createActivityResultObservables(result, { requestCode ->
        startActivityForResult(intent, requestCode)
    })
}

@RequiresApi(16)
@TargetApi(16)
@JvmOverloads
inline fun Fragment.startActivityForResultAsObservable(
        intent: Intent,
        options: Bundle?,
        result: Int = Activity.RESULT_OK
): Observable<Intent> {
    return createActivityResultObservables(result, { requestCode ->
        startActivityForResult(intent, requestCode, options)
    })
}

@JvmOverloads
inline fun android.support.v4.app.Fragment.startActivityForResultAsObservable(
        intent: Intent,
        result: Int = Activity.RESULT_OK
): Observable<Intent> {
    return createActivityResultObservables(result, { requestCode ->
        startActivityForResult(intent, requestCode)
    })
}

@RequiresApi(16)
@TargetApi(16)
@JvmOverloads
inline fun android.support.v4.app.Fragment.startActivityForResultAsObservable(
        intent: Intent,
        options: Bundle?,
        result: Int = Activity.RESULT_OK
): Observable<Intent> {
    return createActivityResultObservables(result, { requestCode ->
        startActivityForResult(intent, requestCode, options)
    })
}