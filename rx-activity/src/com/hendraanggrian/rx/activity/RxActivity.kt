@file:JvmName("RxActivity")
@file:Suppress("NOTHING_TO_INLINE", "UNUSED")

package com.hendraanggrian.rx.activity

import android.app.Activity
import android.app.Fragment
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.SparseArray
import com.hendraanggrian.kota.content.isNotAvailable
import com.hendraanggrian.kota.util.containsKey
import com.hendraanggrian.rx.activity.BuildConfig.KEY_REQUEST_CODE
import com.hendraanggrian.rx.activity.BuildConfig.KEY_RESULT_CODE
import com.hendraanggrian.rx.activity.internal.ActivityStarter
import com.hendraanggrian.rx.activity.internal.TaggableObservableEmitter
import com.hendraanggrian.rx.activity.internal.toStarter
import com.hendraanggrian.rx.activity.internal.toTaggedEmitter
import io.reactivex.Observable
import java.lang.ref.WeakReference
import java.util.*

/**
 * Max range of request code to pass [android.support.v4.app.FragmentActivity.checkForValidRequestCode] precondition.
 */
private const val MAX_REQUEST_CODE = 65535 // 16-bit int

/**
 * Weak reference of Random to generate random number
 * below [MAX_REQUEST_CODE]
 * and not already queued in [QUEUES].
 */
private var RANDOM: WeakReference<Random>? = null

/**
 * Collection of reactive emitters that will emits one-by-one on activity result.
 * Once emitted, emitter will no longer exists in this collection.
 */
private val QUEUES = SparseArray<TaggableObservableEmitter<Intent>>()

private fun ActivityStarter.toObservable(intent: Intent, options: Bundle?, filterResult: Boolean): Observable<Intent> = Observable.create<Intent> { e ->
    if (intent.isNotAvailable(getContext())) {
        e.onError(ActivityNotFoundException("No activity for this intent found."))
    } else {
        val requestCode = newRequestCode()
        QUEUES.append(requestCode, e.toTaggedEmitter().apply { tag = filterResult })
        if (Build.VERSION.SDK_INT >= 16) {
            startActivityForResult(intent, requestCode, options)
        } else {
            startActivityForResult(intent, requestCode)
        }
    }
}

private fun newRequestCode(): Int {
    // attempt to get Random instance from WeakReference
    // when no instance is found, create a new one and save it
    // at this point instance can be ensured not null
    var random: Random?
    if (RANDOM == null) {
        RANDOM = WeakReference(Random())
    }
    random = RANDOM!!.get()
    if (random == null) {
        random = Random()
        RANDOM = WeakReference(random)
    }

    // endless loop until generated request code is unique
    var requestCode: Int
    do {
        requestCode = random.nextInt(MAX_REQUEST_CODE)
    } while (QUEUES.containsKey(requestCode))
    return requestCode
}

fun onActivityResultBy(requestCode: Int, resultCode: Int, data: Intent?) {
    if (QUEUES.containsKey(requestCode)) {
        val e = QUEUES.get(requestCode) as TaggableObservableEmitter<Intent>
        if (!e.isDisposed) {
            checkNotNull(data, { "RxJava can't emit null item." })
            data!!
            data.putExtra(KEY_REQUEST_CODE, requestCode)
            data.putExtra(KEY_RESULT_CODE, resultCode)
            if (e.tag == true && resultCode != Activity.RESULT_OK) {
                e.onError(ActivityCanceledException("Activity started with request code $requestCode is canceled."))
            } else {
                e.onNext(data)
            }
            e.onComplete()
        }
        QUEUES.remove(requestCode)
    }
}

@JvmOverloads
fun Activity.startActivityForOk(
        intent: Intent,
        options: Bundle? = null)
        = toStarter().toObservable(intent, options, true)

@JvmOverloads
fun Activity.startActivityForAny(
        intent: Intent,
        options: Bundle? = null)
        = toStarter().toObservable(intent, options, false)

@JvmOverloads
fun Fragment.startActivityForOk(
        intent: Intent,
        options: Bundle? = null)
        = toStarter().toObservable(intent, options, true)

@JvmOverloads
fun Fragment.startActivityForAny(
        intent: Intent,
        options: Bundle? = null)
        = toStarter().toObservable(intent, options, false)

@JvmOverloads
fun android.support.v4.app.Fragment.startActivityForOk(
        intent: Intent,
        options: Bundle? = null)
        = toStarter().toObservable(intent, options, true)

@JvmOverloads
fun android.support.v4.app.Fragment.startActivityForAny(
        intent: Intent,
        options: Bundle? = null)
        = toStarter().toObservable(intent, options, false)

inline fun Intent.getRequestCode(): Int {
    checkIntent(this)
    return getIntExtra(KEY_REQUEST_CODE, 0)
}

inline fun Intent.getResultCode(): Int {
    checkIntent(this)
    return getIntExtra(KEY_RESULT_CODE, 0)
}

inline fun Intent.isResultOk() = getResultCode() == Activity.RESULT_OK

inline fun checkIntent(value: Intent) {
    if (!value.hasExtra(KEY_REQUEST_CODE) || !value.hasExtra(KEY_RESULT_CODE)) {
        throw IllegalStateException("Intent is not associated with RxActivity")
    }
}