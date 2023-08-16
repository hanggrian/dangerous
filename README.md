[![Travis CI](https://img.shields.io/travis/com/hendraanggrian/dangerous)](https://travis-ci.com/github/hendraanggrian/dangerous/)
[![Codecov](https://img.shields.io/codecov/c/github/hendraanggrian/dangerous)](https://codecov.io/gh/hendraanggrian/dangerous/)
[![Maven Central](https://img.shields.io/maven-central/v/com.hendraanggrian.appcompat/dangerous)](https://search.maven.org/artifact/com.hendraanggrian.appcompat/dangerous/)
[![Nexus Snapshot](https://img.shields.io/nexus/s/com.hendraanggrian.appcompat/dangerous?server=https%3A%2F%2Fs01.oss.sonatype.org)](https://s01.oss.sonatype.org/content/repositories/snapshots/com/hendraanggrian/appcompat/dangerous/)
[![Android SDK](https://img.shields.io/badge/sdk-14%2B-informational)](https://developer.android.com/studio/releases/platforms/#4.0)

# Dangerous

Lightweight library to simplify the process of requesting runtime permissions.
Powered by the newer [Activity Result APIs](https://developer.android.com/training/basics/intents/result),
so we no longer have to override `onActivityResult()`.

- Blocking call with `requirePermission`, or use Kotlin DSL
  with `withPermission`.
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
    compile 'com.hendraanggrian.appcompat:dangerous:0.1'
}
```

## Usage

To force user open Settings app, provide the second DSL.

```kotlin
withPermission(Manifest.permission.CAMERA, { settingsIntent ->
    AlertDialog.Builder(this)
        .setTitle("Permission Denied")
        .setMessage("Need to be enabled manually.")
        .setPositiveButton("Go to Settings") { _, _ ->
            startActivity(settingsIntent)
        }
}) { isGranted ->
    if (isGranted) {
        camera.start()
    }
}
```
