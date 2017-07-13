package com.hendraanggrian.rx.activity

import android.app.Activity
import android.app.Fragment
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.SparseArray
import io.reactivex.Observable
import java.lang.ref.WeakReference
import java.util.*

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
object RxActivity {

    /**
     * Max range of request code to pass [android.support.v4.app.FragmentActivity.checkForValidRequestCode] precondition.
     */
    private val MAX_REQUEST_CODE = 65535 // 16-bit int

    /**
     * Weak reference of Random to generate random number
     * below [RxActivity.MAX_REQUEST_CODE]
     * and not already queued in [RxActivity.QUEUES].
     */
    internal var RANDOM_REQUEST_CODE: WeakReference<Random>? = null

    /**
     * Collection of reactive emitters that will emits one-by-one on activity result.
     * Once emitted, emitter will no longer exists in this collection.
     */
    internal val QUEUES = SparseArray<ActivityResultEmitter<*>>()

    internal fun <T> createStarter(type: Int, startable: ActivityStartable, intent: Intent, options: Bundle?): Observable<T> = Observable.create { e ->
        if (intent.resolveActivity(startable.packageManager) == null) {
            e.onError(ActivityNotFoundException("No activity for this intent found."))
        } else {
            val requestCode = generateRequestCode()
            QUEUES.append(requestCode, ActivityResultEmitter.getInstance(type, e))
            if (Build.VERSION.SDK_INT >= 16) {
                startable.startActivityForResult(intent, requestCode, options)
            } else {
                startable.startActivityForResult(intent, requestCode)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (QUEUES.indexOfKey(requestCode) > -1) {
            val e = QUEUES.get(requestCode) as ActivityResultEmitter<Any>
            if (!e.isDisposed) {
                val result = ActivityResult(requestCode, resultCode, data)
                if (e.type == ActivityResultEmitter.TYPE_RESULT) {
                    e.onNext(result)
                } else {
                    if (resultCode == Activity.RESULT_OK) {
                        e.onNext(result.data!!)
                    } else {
                        e.onError(ActivityCanceledException(result.toString()))
                    }
                }
                e.onComplete()
            }
            QUEUES.remove(requestCode)
        }
    }

    internal fun generateRequestCode(): Int {
        if (RANDOM_REQUEST_CODE == null) {
            RANDOM_REQUEST_CODE = WeakReference(Random())
        }
        var random: Random? = RANDOM_REQUEST_CODE!!.get()
        if (random == null) {
            random = Random()
            RANDOM_REQUEST_CODE = WeakReference<Random>(random)
        }
        val requestCode = random.nextInt(MAX_REQUEST_CODE)
        if (QUEUES.indexOfKey(requestCode) < 0) {
            return requestCode
        }
        return generateRequestCode()
    }
}

@JvmOverloads
fun Activity.startForOk(intent: Intent, options: Bundle? = null): Observable<Intent> = RxActivity.createStarter(
        ActivityResultEmitter.TYPE_OK,
        ActivityStartable.fromActivity(this),
        intent,
        options)

@JvmOverloads
fun Activity.startForResult(intent: Intent, options: Bundle? = null): Observable<ActivityResult> = RxActivity.createStarter(
        ActivityResultEmitter.TYPE_RESULT,
        ActivityStartable.fromActivity(this),
        intent,
        options)

@JvmOverloads
fun Fragment.startForOk(intent: Intent, options: Bundle? = null): Observable<Intent> = RxActivity.createStarter(
        ActivityResultEmitter.TYPE_OK,
        ActivityStartable.fromFragment(this),
        intent,
        options)

@JvmOverloads
fun Fragment.startForResult(intent: Intent, options: Bundle? = null): Observable<ActivityResult> = RxActivity.createStarter(
        ActivityResultEmitter.TYPE_RESULT,
        ActivityStartable.fromFragment(this),
        intent,
        options)

@JvmOverloads
fun android.support.v4.app.Fragment.startForOk(intent: Intent, options: Bundle? = null): Observable<Intent> = RxActivity.createStarter(
        ActivityResultEmitter.TYPE_OK,
        ActivityStartable.fromSupportFragment(this),
        intent,
        options)

@JvmOverloads
fun android.support.v4.app.Fragment.startForResult(intent: Intent, options: Bundle? = null): Observable<ActivityResult> = RxActivity.createStarter(
        ActivityResultEmitter.TYPE_RESULT,
        ActivityStartable.fromSupportFragment(this),
        intent,
        options)