Activity Result
===============
Reactive streams to start activity for result.

```kotlin
startActivityForResult(Intent(Intent.ACTION_GET_CONTENT).setType("image/*")) { resultCode, data ->
    if (resultCode == Activity.RESULT_OK) {
        Uri uri = data.getData()
        imageView.setImageUri(uri)
    }
}
```

Usage
-----
`RxActivity` is usable in Activity, Fragment and support Fragment once `onActivityResult` is overriden.

```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    notifyOnActivityResult(requestCode, resultCode, data)
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
    implementation 'com.hendraanggrian:rx-activity:0.9'
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
