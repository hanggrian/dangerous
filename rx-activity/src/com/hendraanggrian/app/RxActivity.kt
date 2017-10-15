package com.hendraanggrian.app

import android.content.Intent
import android.support.v4.util.SparseArrayCompat
import kota.collections.containsKey
import kota.collections.supportSparseArrayOf
import java.lang.ref.WeakReference
import java.util.*

object RxActivity {

    /** Weak reference of Random to generate random number. */
    private var RANDOM: WeakReference<Random?> = WeakReference(null)

    /** Collection of reactive emitters that will emits one-by-one on activity result. Once emitted, emitter is cease to exist from this collection. */
    private val QUEUES: SparseArrayCompat<ActivityResultEmitter> = supportSparseArrayOf()

    /**
     * Attempt to get Random instance from WeakReference.
     * when no instance is found, create a new one and save it.
     * At this point instance can be ensured not null.
     */
    internal val nextRequestCode: Int
        get() {
            var random = RANDOM.get()
            if (random == null) {
                random = Random()
                RANDOM = WeakReference(random)
            }
            var requestCode: Int
            do {
                // 16-bit int, as required by FragmentActivity precondition
                requestCode = random.nextInt(65535) // 16-bit int
            } while (QUEUES.containsKey(requestCode))
            return requestCode
        }

    internal fun append(requestCode: Int, resultEmitter: ActivityResultEmitter) = QUEUES.append(requestCode, resultEmitter)

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val emitter = QUEUES.get(requestCode) ?: return
        when (emitter) {
            is ObservableActivityResultEmitter -> {
                if (!emitter.isDisposed) {
                    if (emitter.resultCode == resultCode) {
                        emitter.onNext(data!!)
                    } else {
                        emitter.onError(ActivityResultException(requestCode, "Activity with request code $requestCode fails expected result code check."))
                    }
                    emitter.onComplete()
                }
            }
            is SingleActivityResultEmitter -> {
                if (!emitter.isDisposed) {
                    if (emitter.resultCode == resultCode) {
                        emitter.onSuccess(data!!)
                    } else {
                        emitter.onError(ActivityResultException(requestCode, "Activity with request code $requestCode fails expected result code check."))
                    }
                }
            }
        }
        QUEUES.remove(requestCode)
    }
}