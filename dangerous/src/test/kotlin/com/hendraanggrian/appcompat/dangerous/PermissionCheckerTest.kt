package com.hendraanggrian.appcompat.dangerous

import android.Manifest.permission.CAMERA
import android.Manifest.permission.RECORD_AUDIO
import androidx.appcompat.app.AppCompatActivity
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.internal.DoNotInstrument
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse

@RunWith(RobolectricTestRunner::class)
@DoNotInstrument
class PermissionCheckerTest {
    private lateinit var activity: AppCompatActivity

    @BeforeTest
    fun setup() {
        activity = Robolectric.buildActivity(TestActivity::class.java).setup().get()
    }

    @Test
    fun checkPermission() {
        assertFalse(activity.hasPermission(CAMERA))
        assertFalse(activity.hasPermissions(CAMERA, RECORD_AUDIO))
        assertFalse(activity.hasPermissions(listOf(CAMERA, RECORD_AUDIO)))
    }
}
