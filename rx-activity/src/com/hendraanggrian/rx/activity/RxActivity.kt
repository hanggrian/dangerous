@file:JvmName("RxActivity")
@file:Suppress("NOTHING_TO_INLINE", "UNUSED")

package com.hendraanggrian.rx.activity

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.annotation.RequiresApi
import com.hendraanggrian.kota.collection.containsKey
import com.hendraanggrian.rx.activity.internal.ActivityResultEmitter
import com.hendraanggrian.rx.activity.internal.QUEUES
import com.hendraanggrian.rx.activity.internal.createActivityResultObservables
import io.reactivex.Observable

inline fun notifyRxActivity(requestCode: Int, resultCode: Int, data: Intent?) {
    if (QUEUES.containsKey(requestCode)) {
        val e = QUEUES.get(requestCode) as ActivityResultEmitter
        if (!e.isDisposed) {
            if (e.resultCode == resultCode) {
                e.onNext(data!!)
            } else {
                e.onError(ActivityResultException(requestCode,
                        "Activity with request code $requestCode fails expected result code check."))
            }
            e.onComplete()
        }
        QUEUES.remove(requestCode)
    }
}

@JvmOverloads
inline fun Activity.startActivityForResultAsObservable(intent: Intent, result: Int = Activity.RESULT_OK): Observable<Intent> {
    return createActivityResultObservables(result, { requestCode ->
        startActivityForResult(intent, requestCode)
    })
}

@RequiresApi(16)
@TargetApi(16)
@JvmOverloads
inline fun Activity.startActivityForResultAsObservable(intent: Intent, options: Bundle?, result: Int = Activity.RESULT_OK): Observable<Intent> {
    return createActivityResultObservables(result, { requestCode ->
        startActivityForResult(intent, requestCode, options)
    })
}

@JvmOverloads
inline fun Fragment.startActivityForResultAsObservable(intent: Intent, result: Int = Activity.RESULT_OK): Observable<Intent> {
    return createActivityResultObservables(result, { requestCode ->
        activity.startActivityForResult(intent, requestCode)
    })
}

@RequiresApi(16)
@TargetApi(16)
@JvmOverloads
inline fun Fragment.startActivityForResultAsObservable(intent: Intent, options: Bundle?, result: Int = Activity.RESULT_OK): Observable<Intent> {
    return createActivityResultObservables(result, { requestCode ->
      activity.startActivityForResult(intent, requestCode, options)
    })
}

@JvmOverloads
inline fun android.support.v4.app.Fragment.startActivityForResultAsObservable(intent: Intent, result: Int = Activity.RESULT_OK): Observable<Intent> {
    return createActivityResultObservables(result, { requestCode ->
      activity.startActivityForResult(intent, requestCode)
    })
}

@SuppressLint("RestrictedApi")
@RequiresApi(16)
@TargetApi(16)
@JvmOverloads
inline fun android.support.v4.app.Fragment.startActivityForResultAsObservable(intent: Intent, options: Bundle?, result: Int = Activity.RESULT_OK): Observable<Intent> {
    return createActivityResultObservables(result, { requestCode ->
      activity.startActivityForResult(intent, requestCode, options)
    })
}
