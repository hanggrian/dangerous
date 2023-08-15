package com.hendraanggrian.appcompat.hallpass

import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * Ask for single permission, or quit activity.
 *
 * @param permission single [android.Manifest.permission].
 */
fun Fragment.requirePermission(permission: String) {
    val activity = requireActivity()
    if (ContextCompat.checkSelfPermission(activity, permission) == PERMISSION_DENIED) {
        registerForActivityResult(RequestPermission()) { isGranted ->
            if (!isGranted) {
                activity.finish()
            }
        }.launch(permission)
    }
}

/**
 * Ask for multiple permissions, or quit activity.
 *
 * @param permissions multiple [android.Manifest.permission].
 */
fun Fragment.requirePermissions(vararg permissions: String) {
    val activity = requireActivity()
    if (permissions.any { ContextCompat.checkSelfPermission(activity, it) == PERMISSION_DENIED }) {
        registerForActivityResult(RequestMultiplePermissions()) { grantMap ->
            if (!grantMap.values.all { it }) {
                activity.finish()
            }
        }.launch(permissions.asInput())
    }
}

/**
 * Launch an activity that request single permission.
 *
 * @param permission single [android.Manifest.permission].
 * @param onRequest triggered on activity launch, or immediately if already granted.
 */
fun Fragment.withPermission(
    permission: String,
    onRequest: (isGranted: Boolean) -> Unit
) {
    val context = requireContext()
    when (PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(context, permission) -> onRequest(true)
        else -> registerForActivityResult(RequestPermission(), onRequest).launch(permission)
    }
}

/**
 * Launch an activity that request single permission.
 *
 * @param permission single [android.Manifest.permission].
 * @param onRequest triggered on activity launch, or immediately if already granted.
 * @param onDeclined triggered when permission is always denied, launch settings intent.
 */
fun Fragment.withPermission(
    permission: String,
    onRequest: (isGranted: Boolean) -> Unit,
    onDeclined: (settingsIntent: Intent) -> Unit
) {
    val activity = requireActivity()
    when {
        ContextCompat.checkSelfPermission(activity, permission) == PERMISSION_GRANTED ->
            onRequest(true)
        ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) ->
            onDeclined(activity.settingsIntent)
        else -> registerForActivityResult(RequestPermission(), onRequest).launch(permission)
    }
}

/**
 * Launch an activity that request multiple permissions.
 *
 * @param permissions multiple [android.Manifest.permission].
 * @param onRequest triggered on activity launch, or immediately if already granted.
 */
fun Fragment.withPermissions(
    vararg permissions: String,
    onRequest: (grantMap: Map<String, Boolean>) -> Unit
) {
    val context = requireContext()
    when {
        permissions.all { ContextCompat.checkSelfPermission(context, it) == PERMISSION_GRANTED } ->
            onRequest(permissions.asMap())
        else -> registerForActivityResult(RequestMultiplePermissions(), onRequest)
            .launch(permissions.asInput())
    }
}

/**
 * Launch an activity that request multiple permissions.
 *
 * @param permissions multiple [android.Manifest.permission].
 * @param onRequest triggered on activity launch, or immediately if already granted.
 * @param onDeclined triggered when permission is always denied, launch settings intent.
 */
fun Fragment.withPermissions(
    vararg permissions: String,
    onRequest: (grantMap: Map<String, Boolean>) -> Unit,
    onDeclined: (settingsIntent: Intent) -> Unit
) {
    val activity = requireActivity()
    when {
        permissions.all { ContextCompat.checkSelfPermission(activity, it) == PERMISSION_GRANTED } ->
            onRequest(permissions.asMap())
        permissions.all { ActivityCompat.shouldShowRequestPermissionRationale(activity, it) } ->
            onDeclined(activity.settingsIntent)
        else -> registerForActivityResult(RequestMultiplePermissions(), onRequest)
            .launch(permissions.asInput())
    }
}
