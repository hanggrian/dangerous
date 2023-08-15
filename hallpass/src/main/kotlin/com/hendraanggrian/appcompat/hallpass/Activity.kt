package com.hendraanggrian.appcompat.hallpass

import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Ask for single permission, or quit activity.
 *
 * @param permission single [android.Manifest.permission].
 */
fun ComponentActivity.requirePermission(permission: String) {
    if (ContextCompat.checkSelfPermission(this, permission) == PERMISSION_DENIED) {
        registerForActivityResult(RequestPermission()) { isGranted ->
            if (!isGranted) {
                finish()
            }
        }.launch(permission)
    }
}

/**
 * Ask for multiple permissions, or quit activity.
 *
 * @param permissions multiple [android.Manifest.permission].
 */
fun ComponentActivity.requirePermissions(vararg permissions: String) {
    if (permissions.any { ContextCompat.checkSelfPermission(this, it) == PERMISSION_DENIED }) {
        registerForActivityResult(RequestMultiplePermissions()) { grantMap ->
            if (!grantMap.values.all { it }) {
                finish()
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
fun ComponentActivity.withPermission(
    permission: String,
    onRequest: (isGranted: Boolean) -> Unit
): Unit = when (PERMISSION_GRANTED) {
    ContextCompat.checkSelfPermission(this, permission) -> onRequest(true)
    else -> registerForActivityResult(RequestPermission(), onRequest).launch(permission)
}

/**
 * Launch an activity that request single permission.
 *
 * @param permission single [android.Manifest.permission].
 * @param onRequest triggered on activity launch, or immediately if already granted.
 * @param onDeclined triggered when permission is always denied, launch settings intent.
 */
fun ComponentActivity.withPermission(
    permission: String,
    onRequest: (isGranted: Boolean) -> Unit,
    onDeclined: (settingsIntent: Intent) -> Unit
): Unit = when {
    ContextCompat.checkSelfPermission(this, permission) == PERMISSION_GRANTED -> onRequest(true)
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
    onRequest: (grantMap: Map<String, Boolean>) -> Unit
): Unit = when {
    permissions.all { ContextCompat.checkSelfPermission(this, it) == PERMISSION_GRANTED } ->
        onRequest(permissions.asMap())
    else -> registerForActivityResult(RequestMultiplePermissions(), onRequest)
        .launch(permissions.asInput())
}

/**
 * Launch an activity that request multiple permissions.
 *
 * @param permissions multiple [android.Manifest.permission].
 * @param onRequest triggered on activity launch, or immediately if already granted.
 * @param onDeclined triggered when permission is always denied, launch settings intent.
 */
fun ComponentActivity.withPermissions(
    vararg permissions: String,
    onRequest: (grantMap: Map<String, Boolean>) -> Unit,
    onDeclined: (settingsIntent: Intent) -> Unit
): Unit = when {
    permissions.all { ContextCompat.checkSelfPermission(this, it) == PERMISSION_GRANTED } ->
        onRequest(permissions.asMap())
    permissions.all { ActivityCompat.shouldShowRequestPermissionRationale(this, it) } ->
        onDeclined(settingsIntent)
    else -> registerForActivityResult(RequestMultiplePermissions(), onRequest)
        .launch(permissions.asInput())
}
