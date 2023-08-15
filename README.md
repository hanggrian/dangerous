[![download](https://api.bintray.com/packages/hendraanggrian/appcompat/launchy/images/download.svg)](https://bintray.com/hendraanggrian/appcompat/launchy/_latestVersion)
[![build](https://travis-ci.com/hendraanggrian/launchy.svg)](https://travis-ci.com/hendraanggrian/launchy)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)
[![license](https://img.shields.io/github/license/hendraanggrian/launchy)](http://www.apache.org/licenses/LICENSE-2.0)

# Hallpass

Kotlin-focused library to simplify the process of requesting permissions.

- Blocking call using `requirePermission`.
- Or use Kotlin DSL using `withPermission`.
- Force user to open settings when the permission is always declined.

```kotlin
requirePermission(Manifest.permission.CAMERA)
camera.start()

// or

withPermission(Manifest.permission.CAMERA) { isGranted ->
    if (isGranted) {
        camera.start()
    }
}
```

## Download

```gradle
repositories {
    google()
    mavenCentral()
}

dependencies {
    compile 'com.hendraanggrian.appcompat:hallpass:0.1'
}
```

## Usage

To force user open Settings app, provide the second DSL.

```kotlin
requestPermissions(Manifest.permission.CAMERA, { isGranted ->
    if (isGranted) {
        camera.start()
    }
}) { settingsIntent ->
    AlertDialog.Builder(this)
        .setTitle("Permission Denied")
        .setMessage("Need to be enabled manually.")
        .setPositiveButton("Go to Settings") { _, _ ->
            startActivity(settingsIntent)
        }
}
```
