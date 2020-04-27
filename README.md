[![download](https://api.bintray.com/packages/hendraanggrian/appcompat/launchy/images/download.svg)](https://bintray.com/hendraanggrian/appcompat/launchy/_latestVersion)
[![build](https://travis-ci.com/hendraanggrian/launchy.svg)](https://travis-ci.com/hendraanggrian/launchy)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)
[![license](https://img.shields.io/github/license/hendraanggrian/launchy)](http://www.apache.org/licenses/LICENSE-2.0)

Launchy
=======
Kotlin-focused library to simplify the process of handling result from an Activity and requesting permissions.
 * Direct activity and permission result. Traditionally, code must be split into `onActivityResult` and `onRequestPermissionsResult`.
 * Never have to deal with request code again, they are auto-generated.

Download
--------
```gradle
repositories {
    google()
    jcenter()
}

dependencies {
    compile 'com.hendraanggrian.appcompat:launchy:0.1'
}
```

Usage
-----
Start activity for result from Activity, Fragment, or support Fragment.

```kotlin
launchActivity(Intent(Intent.ACTION_GET_CONTENT).setType("image/*")) { resultCode, data ->
    if (resultCode == Activity.RESULT_OK) {
        val uri = data.getData()
        imageView.setImageUri(uri)   
    }
}


override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    Launchy.onActivityResult(this, requestCode, resultCode, data)
}
```

Request permission from Activity, Fragment, or support Fragment.

```kotlin
launchPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE) { granted ->
    if (granted) {
        // do some shit   
    }
}


override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    Launchy.onRequestPermissionsResult(this, requestCode, grantResults)
}
```