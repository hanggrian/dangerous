[![download](https://api.bintray.com/packages/hendraanggrian/appcompat/launchy/images/download.svg)](https://bintray.com/hendraanggrian/appcompat/launchy/_latestVersion)
[![build](https://travis-ci.com/hendraanggrian/launchy.svg)](https://travis-ci.com/hendraanggrian/launchy)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)
[![license](https://img.shields.io/github/license/hendraanggrian/launchy)](http://www.apache.org/licenses/LICENSE-2.0)

# Hall Pass

Kotlin-focused library to simplify the process of handling result from an Activity and requesting permissions.

* Put activity/permission result logic directly in the caller, no more `if-else` in `onActivityResult` and `onRequestPermissionsResult`.
* Never have to deal with request code again, they are auto-generated.

```kotlin
launchActivity(Intent(Intent.ACTION_GET_CONTENT).setType("image/*")) { resultCode, data ->
    if (resultCode == Activity.RESULT_OK) {
        val uri = data.getData()
        imageView.setImageUri(uri)
    }
}

launchPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE) { granted ->
    if (granted) {
        openCamera()
    }
}
```

## Download

```gradle
repositories {
    google()
    jcenter()
}

dependencies {
    compile 'com.hendraanggrian.appcompat:launchy:0.1'
}
```

## Usage

### Launching activity

Start activity for result from Activity, Fragment, or support Fragment.

```kotlin
class MyActivity : Activity() {

    fun onClick(view: View) {
        launchActivity(Intent(Intent.ACTION_GET_CONTENT).setType("image/*")) { resultCode, data ->
            if (resultCode == Activity.RESULT_OK) {
                val uri = data.getData()
                imageView.setImageUri(uri)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) =
        Launchy.onActivityResult(this, requestCode, resultCode, data)
}
```

### Requesting permissions

Request permission is a little different, it can only be implanted in Activity.
That activity is then used as type `T` in `launchPermission<T>`.

```kotlin
class MyActivity : Activity() {

    override fun onCreate() {
        super.onCreate()
        launchPermissions<MyActivity>(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE) { isGranted ->
            if (isGranted) {
                openCamera()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) =
        Launchy.onRequestPermissionsResult(this, requestCode, grantResults)
}
```
