@file:JvmMultifileClass
@file:JvmName("PermissionLauncherKt")

package com.hendraanggrian.appcompat.dangerous

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.app.ActivityCompat

/**
 * Ask for single permission, or quit activity.
 *
 * @param permission single [android.Manifest.permission].
 */
fun ComponentActivity.requirePermission(permission: String) {
    if (hasPermission(permission)) {
        return
    }
    registerForActivityResult(RequestPermission()) { isGranted ->
        if (!isGranted) {
            finish()
        }
    }.launch(permission)
}

/**
 * Ask for multiple permissions, or quit activity.
 *
 * @param permissions multiple [android.Manifest.permission].
 */
fun ComponentActivity.requirePermissions(vararg permissions: String) {
    if (hasPermissions(*permissions)) {
        return
    }
    registerForActivityResult(RequestMultiplePermissions()) { isGranted ->
        if (!isGranted.values.all { it }) {
            finish()
        }
    }.launch(permissions.duplicate())
}

/**
 * Ask for multiple permissions, or quit activity.
 *
 * @param permissions multiple [android.Manifest.permission].
 */
fun ComponentActivity.requirePermissions(permissions: Collection<String>) {
    if (hasPermissions(permissions)) {
        return
    }
    registerForActivityResult(RequestMultiplePermissions()) { isGranted ->
        if (!isGranted.values.all { it }) {
            finish()
        }
    }.launch(permissions.toTypedArray())
}

/**
 * Launch an activity that request single permission.
 *
 * @param permission single [android.Manifest.permission].
 * @param onRequest triggered on activity launch, or immediately if already granted.
 */
fun ComponentActivity.withPermission(
    permission: String,
    onRequest: (isGranted: Boolean) -> Unit
): Unit = when {
    hasPermission(permission) -> onRequest(true)
    else -> registerForActivityResult(RequestPermission(), onRequest).launch(permission)
}

/**
 * Launch an activity that request single permission.
 *
 * @param permission single [android.Manifest.permission].
 * @param onDeclined triggered when permission is always denied, launch settings intent.
 * @param onRequest triggered on activity launch, or immediately if already granted.
 */
fun ComponentActivity.withPermission(
    permission: String,
    onDeclined: (settingsIntent: Intent) -> Unit,
    onRequest: (isGranted: Boolean) -> Unit
): Unit = when {
    hasPermission(permission) -> onRequest(true)
    ActivityCompat.shouldShowRequestPermissionRationale(this, permission) ->
        onDeclined(settingsIntent)
    else -> registerForActivityResult(RequestPermission(), onRequest).launch(permission)
}

/**
 * Launch an activity that request multiple permissions.
 *
 * @param permissions multiple [android.Manifest.permission].
 * @param onRequest triggered on activity launch, or immediately if already granted.
 */
fun ComponentActivity.withPermissions(
    vararg permissions: String,
    onRequest: (isGranted: Map<String, Boolean>) -> Unit
): Unit = when {
    hasPermissions(*permissions) -> onRequest(permissions.associateWith { true })
    else -> registerForActivityResult(RequestMultiplePermissions(), onRequest)
        .launch(permissions.duplicate())
}

/**
 * Launch an activity that request multiple permissions.
 *
 * @param permissions multiple [android.Manifest.permission].
 * @param onDeclined triggered when permission is always denied, launch settings intent.
 * @param onRequest triggered on activity launch, or immediately if already granted.
 */
fun ComponentActivity.withPermissions(
    vararg permissions: String,
    onDeclined: (settingsIntent: Intent) -> Unit,
    onRequest: (isGranted: Map<String, Boolean>) -> Unit
): Unit = when {
    hasPermissions(*permissions) -> onRequest(permissions.associateWith { true })
    permissions.all { ActivityCompat.shouldShowRequestPermissionRationale(this, it) } ->
        onDeclined(settingsIntent)
    else -> registerForActivityResult(RequestMultiplePermissions(), onRequest)
        .launch(permissions.duplicate())
}

/**
 * Launch an activity that request multiple permissions.
 *
 * @param permissions multiple [android.Manifest.permission].
 * @param onRequest triggered on activity launch, or immediately if already granted.
 */
fun ComponentActivity.withPermissions(
    permissions: Collection<String>,
    onRequest: (isGranted: Map<String, Boolean>) -> Unit
): Unit = when {
    hasPermissions(permissions) -> onRequest(permissions.associateWith { true })
    else -> registerForActivityResult(RequestMultiplePermissions(), onRequest)
        .launch(permissions.toTypedArray())
}

/**
 * Launch an activity that request multiple permissions.
 *
 * @param permissions multiple [android.Manifest.permission].
 * @param onDeclined triggered when permission is always denied, launch settings intent.
 * @param onRequest triggered on activity launch, or immediately if already granted.
 */
fun ComponentActivity.withPermissions(
    permissions: Collection<String>,
    onDeclined: (settingsIntent: Intent) -> Unit,
    onRequest: (isGranted: Map<String, Boolean>) -> Unit
): Unit = when {
    hasPermissions(permissions) -> onRequest(permissions.associateWith { true })
    permissions.all { ActivityCompat.shouldShowRequestPermissionRationale(this, it) } ->
        onDeclined(settingsIntent)
    else -> registerForActivityResult(RequestMultiplePermissions(), onRequest)
        .launch(permissions.toTypedArray())
}
