RxActivity
==========
Reactive streams to start activity for result.

```java
startForResultAsObservable(activity, new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"))
    .subscribe(data -> {
        Uri uri = data.getData();
        imageView.setImageUri(uri);
    });
```

Usage
-----
`RxActivity` is usable in Activity, Fragment and support Fragment once `onActivityResult` is overriden.

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    RxActivity.onActivityResult(requestCode, resultCode, data);
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
    implementation 'io.reactivex.rxjava2:rxjava:2.1.5'
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
