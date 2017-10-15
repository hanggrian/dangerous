package com.hendraanggrian.app

import android.content.Intent
import android.support.v4.util.SparseArrayCompat
import io.reactivex.Observable
import kota.collections.containsKey
import kota.collections.supportSparseArrayOf
import java.lang.ref.WeakReference
import java.util.*

object RxActivity {

    /** Max range of request code to pass [android.support.v4.app.FragmentActivity.checkForValidRequestCode] precondition. */
    const val MAX_REQUEST_CODE = 65535 // 16-bit int

    /** Weak reference of Random to generate random number below [MAX_REQUEST_CODE] and not already queued in [QUEUES]. */
    var RANDOM: WeakReference<Random>? = null

    /**
     * Collection of reactive emitters that will emits one-by-one on activity result.
     * Once emitted, emitter will no longer exists in this collection.
     */
    val QUEUES: SparseArrayCompat<ActivityResultEmitter> = supportSparseArrayOf()

    @PublishedApi
    internal fun createActivityResultObservables(result: Int, activityStarter: (Int) -> Unit): Observable<Intent> = Observable.create<Intent> { e ->
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

        QUEUES.append(requestCode, ActivityResultEmitterImpl(result, e))
        activityStarter.invoke(requestCode)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (QUEUES.containsKey(requestCode)) {
            val e = QUEUES.get(requestCode) as ActivityResultEmitter
            if (!e.isDisposed) {
                if (e.resultCode == resultCode) {
                    e.onNext(data!!)
                } else {
                    e.onError(ActivityResultException(requestCode, "Activity with request code $requestCode fails expected result code check."))
                }
                e.onComplete()
            }
            QUEUES.remove(requestCode)
        }
    }
}