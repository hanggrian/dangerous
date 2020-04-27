package com.example.launchy

import android.Manifest
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.content.PermissionChecker
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.snackbar.bannerbar
import com.hendraanggrian.appcompat.launchy.Launchy
import com.hendraanggrian.appcompat.launchy.launchPermission

class MainFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.fragment_main)
        findPreference<Preference>("launchActivity")!!.setOnPreferenceClickListener {
            false
        }
        findPreference<Preference>("launchPermissions")!!.setOnPreferenceClickListener {
            PermissionsDialog().show(childFragmentManager, null)
            false
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d("FRAGMENT1", requestCode.toString())
    }

    class PermissionsDialog : AppCompatDialogFragment() {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val requestedPermissions = mutableListOf<String>()
            return AlertDialog.Builder(context!!)
                .setMultiChoiceItems(
                    arrayOf("Calendar", "Camera", "Contacts", "SMS"),
                    booleanArrayOf(false, false, false, false)
                ) { _, which, isChecked ->
                    if (isChecked) {
                        when (which) {
                            0 -> requestedPermissions += Manifest.permission.READ_CALENDAR
                            1 -> requestedPermissions += Manifest.permission.CAMERA
                            2 -> requestedPermissions += Manifest.permission.READ_CONTACTS
                            3 -> requestedPermissions += Manifest.permission.READ_SMS
                        }
                    } else {
                        when (which) {
                            0 -> requestedPermissions -= Manifest.permission.READ_CALENDAR
                            1 -> requestedPermissions -= Manifest.permission.CAMERA
                            2 -> requestedPermissions -= Manifest.permission.READ_CONTACTS
                            3 -> requestedPermissions -= Manifest.permission.READ_SMS
                        }
                    }
                }
                .setNegativeButton(android.R.string.cancel) { _, _ -> }
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    launchPermission(*requestedPermissions.toTypedArray()) { isGranted ->
                        dialog!!.window!!.findViewById<FrameLayout>(android.R.id.content).bannerbar(
                            when {
                                isGranted -> "All permissions are granted"
                                else -> "At least one permission is denied"
                            }
                        ) {
                            subtitle = buildString {
                                val getResult: (permission: String) -> String = {
                                    when (PermissionChecker.PERMISSION_GRANTED) {
                                        PermissionChecker.checkSelfPermission(context!!, it) -> "granted."
                                        else -> " denied."
                                    }
                                }
                                appendln("Calendar is ${getResult(Manifest.permission.READ_CALENDAR)}")
                                appendln("Camera is ${getResult(Manifest.permission.CAMERA)}")
                                appendln("Contacts is ${getResult(Manifest.permission.READ_CONTACTS)}")
                                append("SMS is ${getResult(Manifest.permission.READ_SMS)}")
                            }
                        }
                    }
                }
                .create()
        }

        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            Log.d("FRAGMENT2", requestCode.toString())
        }
    }
}