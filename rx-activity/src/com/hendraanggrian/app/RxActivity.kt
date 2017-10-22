package com.hendraanggrian.app

import android.content.Intent
import android.support.v4.util.SparseArrayCompat
import java.lang.ref.WeakReference
import java.util.*

object RxActivity {

    /** Weak reference of Random to generate random number. */
    private var RANDOM: WeakReference<Random?> = WeakReference(null)

    /** Collection of reactive emitters that will emits one-by-one on activity result. Once emitted, emitter is cease to exist from this collection. */
    private val EMITTERS: SparseArrayCompat<ActivityResultEmitter> = SparseArrayCompat()

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
                requestCode = random.nextInt(65535)
            } while (EMITTERS.indexOfKey(requestCode) > -1)
            return requestCode
        }

    internal fun append(requestCode: Int, emitter: ActivityResultEmitter) = EMITTERS.append(requestCode, emitter)

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val emitter = EMITTERS.get(requestCode) ?: return
        EMITTERS.remove(requestCode)
        if (emitter.isDisposed()) return
        when (emitter) {
            is ObservableActivityResultEmitter -> {
                if (emitter.resultCode != resultCode) emitter.onError(ActivityResultException(requestCode, resultCode, data))
                else emitter.onNext(data!!)
                emitter.onComplete()
            }
            is SingleActivityResultEmitter -> {
                if (emitter.resultCode != resultCode) emitter.onError(ActivityResultException(requestCode, resultCode, data))
                else emitter.onSuccess(data!!)
            }
            is CompletableActivityResultEmitter -> {
                if (emitter.resultCode != resultCode) emitter.onError(ActivityResultException(requestCode, resultCode, data))
                emitter.onComplete()
            }
        }
    }
}