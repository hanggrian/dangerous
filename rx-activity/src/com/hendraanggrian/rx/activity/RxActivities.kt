@file:JvmName("RxActivities")

package com.hendraanggrian.rx.activity

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import com.hendraanggrian.rx.activity.internal.ActivityResultEmitter
import com.hendraanggrian.rx.activity.internal.ActivityStartable
import io.reactivex.Observable

@JvmOverloads
fun Activity.startActivityForResultOk(intent: Intent, options: Bundle? = null): Observable<Intent> = RxActivity.createStarter(
        ActivityResultEmitter.TYPE_OK,
        ActivityStartable.fromActivity(this),
        intent,
        options)

@JvmOverloads
fun Activity.startActivityForResultBy(intent: Intent, options: Bundle? = null): Observable<ActivityResult> = RxActivity.createStarter(
        ActivityResultEmitter.TYPE_RESULT,
        ActivityStartable.fromActivity(this),
        intent,
        options)

@JvmOverloads
fun Fragment.startActivityForResultOk(intent: Intent, options: Bundle? = null): Observable<Intent> = RxActivity.createStarter(
        ActivityResultEmitter.TYPE_OK,
        ActivityStartable.fromFragment(this),
        intent,
        options)

@JvmOverloads
fun Fragment.startActivityForResultBy(intent: Intent, options: Bundle? = null): Observable<ActivityResult> = RxActivity.createStarter(
        ActivityResultEmitter.TYPE_RESULT,
        ActivityStartable.fromFragment(this),
        intent,
        options)

@JvmOverloads
fun android.support.v4.app.Fragment.startActivityForResultOk(intent: Intent, options: Bundle? = null): Observable<Intent> = RxActivity.createStarter(
        ActivityResultEmitter.TYPE_OK,
        ActivityStartable.fromSupportFragment(this),
        intent,
        options)

@JvmOverloads
fun android.support.v4.app.Fragment.startActivityForResultBy(intent: Intent, options: Bundle? = null): Observable<ActivityResult> = RxActivity.createStarter(
        ActivityResultEmitter.TYPE_RESULT,
        ActivityStartable.fromSupportFragment(this),
        intent,
        options)