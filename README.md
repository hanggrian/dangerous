result
======
Kotlin-focused library to simplify the process of handling result from an Activity and requesting permissions.
 * Direct activity and permission result. Traditionally, code must be split into `onActivityResult` and `onRequestPermissionsResult`.
 * Never have to deal with request code again, they are auto-generated.

Usage
-----
Start activity for result from Activity, Fragment, or support Fragment.

```kotlin
startActivityForResult(Intent(Intent.ACTION_GET_CONTENT).setType("image/*")) { resultCode, data ->
    if (resultCode == Activity.RESULT_OK) {
        val uri = data.getData()
        imageView.setImageUri(uri)   
    }
}


override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    onActivityResult2(requestCode, resultCode, data)
}
```

Request permission from Activity, Fragment, or support Fragment.

```kotlin
requestPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE) { granted ->
    if (granted) {
        // do some shit   
    }
}


override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    onRequestPermissionsResult2(requestCode, permissions, grantResults)
}
```

Download
--------
```gradle
repositories {
    google()
    jcenter()
}

dependencies {
    compile 'com.hendraanggrian:result:0.1'
}
```

License
-------
    Copyright 2017 Hendra Anggrian

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
