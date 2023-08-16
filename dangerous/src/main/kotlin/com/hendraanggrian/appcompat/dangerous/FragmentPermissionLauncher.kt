@file:JvmMultifileClass
@file:JvmName("PermissionLauncherKt")

package com.hendraanggrian.appcompat.dangerous

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

/**
 * Ask for single permission, or quit activity.
 *
 * @param permission single [android.Manifest.permission].
 */
fun Fragment.requirePermission(permission: String) {
    val activity = requireActivity()
    if (activity.hasPermissions(permission)) {
        return
    }
    registerForActivityResult(RequestPermission()) { isGranted ->
        if (!isGranted) {
            activity.finish()
        }
    }.launch(permission)
}

/**
 * Ask for multiple permissions, or quit activity.
 *
 * @param permissions multiple [android.Manifest.permission].
 */
fun Fragment.requirePermissions(vararg permissions: String) {
    val activity = requireActivity()
    if (activity.hasPermissions(*permissions)) {
        return
    }
    registerForActivityResult(RequestMultiplePermissions()) { isGranted ->
        if (!isGranted.values.all { it }) {
            activity.finish()
        }
    }.launch(permissions.duplicate())
}

/**
 * Ask for multiple permissions, or quit activity.
 *
 * @param permissions multiple [android.Manifest.permission].
 */
fun Fragment.requirePermissions(permissions: Collection<String>) {
    val activity = requireActivity()
    if (activity.hasPermissions(permissions)) {
        return
    }
    registerForActivityResult(RequestMultiplePermissions()) { isGranted ->
        if (!isGranted.values.all { it }) {
            activity.finish()
        }
    }.launch(permissions.toTypedArray())
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
    when {
        context.hasPermission(permission) -> onRequest(true)
        else -> registerForActivityResult(RequestPermission(), onRequest).launch(permission)
    }
}

/**
 * Launch an activity that request single permission.
 *
 * @param permission single [android.Manifest.permission].
 * @param onDeclined triggered when permission is always denied, launch settings intent.
 * @param onRequest triggered on activity launch, or immediately if already granted.
 */
fun Fragment.withPermission(
    permission: String,
    onDeclined: (settingsIntent: Intent) -> Unit,
    onRequest: (isGranted: Boolean) -> Unit
) {
    val activity = requireActivity()
    when {
        activity.hasPermission(permission) -> onRequest(true)
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
    onRequest: (isGranted: Map<String, Boolean>) -> Unit
) {
    val context = requireContext()
    when {
        context.hasPermissions(*permissions) -> onRequest(permissions.associateWith { true })
        else -> registerForActivityResult(RequestMultiplePermissions(), onRequest)
            .launch(permissions.duplicate())
    }
}

/**
 * Launch an activity that request multiple permissions.
 *
 * @param permissions multiple [android.Manifest.permission].
 * @param onDeclined triggered when permission is always denied, launch settings intent.
 * @param onRequest triggered on activity launch, or immediately if already granted.
 */
fun Fragment.withPermissions(
    vararg permissions: String,
    onDeclined: (settingsIntent: Intent) -> Unit,
    onRequest: (isGranted: Map<String, Boolean>) -> Unit
) {
    val activity = requireActivity()
    when {
        activity.hasPermissions(*permissions) -> onRequest(permissions.associateWith { true })
        permissions.all { ActivityCompat.shouldShowRequestPermissionRationale(activity, it) } ->
            onDeclined(activity.settingsIntent)
        else -> registerForActivityResult(RequestMultiplePermissions(), onRequest)
            .launch(permissions.duplicate())
    }
}

/**
 * Launch an activity that request multiple permissions.
 *
 * @param permissions multiple [android.Manifest.permission].
 * @param onRequest triggered on activity launch, or immediately if already granted.
 */
fun Fragment.withPermissions(
    permissions: Collection<String>,
    onRequest: (isGranted: Map<String, Boolean>) -> Unit
) {
    val context = requireContext()
    when {
        context.hasPermissions(permissions) -> onRequest(permissions.associateWith { true })
        else -> registerForActivityResult(RequestMultiplePermissions(), onRequest)
            .launch(permissions.toTypedArray())
    }
}

/**
 * Launch an activity that request multiple permissions.
 *
 * @param permissions multiple [android.Manifest.permission].
 * @param onDeclined triggered when permission is always denied, launch settings intent.
 * @param onRequest triggered on activity launch, or immediately if already granted.
 */
fun Fragment.withPermissions(
    permissions: Collection<String>,
    onDeclined: (settingsIntent: Intent) -> Unit,
    onRequest: (isGranted: Map<String, Boolean>) -> Unit
) {
    val activity = requireActivity()
    when {
        activity.hasPermissions(permissions) -> onRequest(permissions.associateWith { true })
        permissions.all { ActivityCompat.shouldShowRequestPermissionRationale(activity, it) } ->
            onDeclined(activity.settingsIntent)
        else -> registerForActivityResult(RequestMultiplePermissions(), onRequest)
            .launch(permissions.toTypedArray())
    }
}
