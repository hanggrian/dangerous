package com.example.lane

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.android.cameraview.CameraView
import com.google.android.cameraview.CameraView.*
import com.hendraanggrian.bundler.Bundler
import com.hendraanggrian.lane.onActivityResult2
import com.hendraanggrian.lane.onRequestPermissionsResult2
import com.hendraanggrian.lane.requestPermissions
import com.hendraanggrian.lane.startActivityForOkResult
import com.hendraanggrian.preferencer.Preferencer
import com.hendraanggrian.preferencer.Saver
import com.hendraanggrian.preferencer.annotations.BindPreference
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kota.toast
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream

class CameraActivity : AppCompatActivity() {

    @BindPreference("camera_flash") @JvmField @CameraView.Flash var prefFlash = FLASH_AUTO
    @BindPreference("camera_facing") @JvmField @CameraView.Facing var prefFacing = FACING_BACK

    private lateinit var saver: Saver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""
        cameraView.addCallback(object : CameraView.Callback() {
            override fun onPictureTaken(cameraView: CameraView, data: ByteArray) {
                requestPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE) { granted ->
                    if (granted) {
                        Observable.just(data)
                                .observeOn(Schedulers.io())
                                .subscribe({ bytes ->
                                    val file = File(getExternalFilesDir(null), "image.jpg")
                                    val stream = BufferedOutputStream(FileOutputStream(file))
                                    stream.write(bytes)
                                    stream.flush()
                                    stream.close()
                                    startActivity(Intent(this, ImageActivity::class.java)
                                            .putExtras(Bundler.wrapExtras(ImageActivity::class.java, file, null)))
                                }, { e ->
                                    runOnUiThread {
                                        toast(e.message!!)
                                    }
                                })
                    } else {
                        toast("Storage permission denied!")
                    }
                }
            }
        })
        imageButtonClose.setOnClickListener { finish() }
        imageButtonCapture.setOnClickListener { cameraView.takePicture() }
        imageButtonGallery.setOnClickListener {
            startActivityForOkResult(Intent(Intent.ACTION_GET_CONTENT).setType("image/*")) { data ->
                val intent = Intent(this, ImageActivity::class.java)
                intent.putExtras(Bundler.wrapExtras(ImageActivity::class.java, null, data!!.data))
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requestPermissions(Manifest.permission.CAMERA) { granted ->
            if (granted) {
                // fix for CameraView crashing on newly granted permission requestPermissionsAsObservable
                // if (!alreadyGranted) ProcessPhoenix.triggerRebirth(this)
                cameraView.start()
            } else {
                toast("Camera permission denied!")
                finish()
            }
        }
    }

    override fun onPause() {
        cameraView.stop()
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.camera, menu)
        saver = Preferencer.bind(this)
        updateFlashItem(menu.findItem(R.id.item_flash))
        updateFacingItem(menu.findItem(R.id.item_facing))
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_flash) {
            prefFlash = when (prefFlash) {
                FLASH_AUTO -> FLASH_ON
                FLASH_ON -> FLASH_OFF
                else -> FLASH_AUTO
            }
            updateFlashItem(item)
        } else if (item.itemId == R.id.item_facing) {
            prefFacing = when (prefFacing) {
                FACING_BACK -> FACING_FRONT
                else -> FACING_BACK
            }
            updateFacingItem(item)
        }
        saver.saveAll()
        return super.onOptionsItemSelected(item)
    }

    private fun updateFlashItem(item: MenuItem) {
        item.setIcon(when (prefFlash) {
            FLASH_AUTO -> R.drawable.ic_flash_auto_white_24dp
            FLASH_ON -> R.drawable.ic_flash_on_white_24dp
            else -> R.drawable.ic_flash_off_white_24dp
        })
        cameraView.flash = prefFlash
    }

    private fun updateFacingItem(item: MenuItem) {
        item.setIcon(when (prefFacing) {
            FACING_BACK -> R.drawable.ic_camera_rear_white_24dp
            else -> R.drawable.ic_camera_front_white_24dp
        })
        cameraView.facing = prefFacing
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        onActivityResult2(requestCode, resultCode, data)
        if (!cameraView.isCameraOpened) cameraView.start()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult2(requestCode, permissions, grantResults)
    }
}