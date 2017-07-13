RxActivity
==========
Reactive streams to start activity for result.

```java
RxActivity.startForResult(activity, new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"))
    .subscribe(result -> {
        if (result.resultCode == Activity.RESULT_OK) {
            Intent data = result.data;
            Uri uri = data.getData();
            imageView.setImageUri(uri);
        }
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

#### Start activity for OK result
Start activity that only emits `Intent` result if result code is `Activity.RESULT_OK`.
Will throw `ActivityCanceledException` if the result code is `Activity.RESULT_CANCELED`,
and `ActivityNotFoundException` if no activity can handle intent input.
```java
RxActivity.startForOk(activity, intent)
    .subscribe(data -> {
        // result code is Activity.RESULT_OK
        // proceed to handle activity result
    
    }, throwable -> {
        // result code is Activity.RESULT_CANCELED
    });
```

#### Start activity for any result
Start actvity that emits `ActivityResult`, which is a tuple of request code, result code, and `Intent` result.
Will only throw `ActivityNotFoundException` if no activity can handle intent input.
```java
RxActivity.startForAny(activity, intent)
    .subscribe(activityResult -> {
        // 
        int requestCode = activityResult.requestCode;
        int resultCode = activityResult.resultCode;
        Intent data = activityResult.data;
        
        // TODO: use fields
    });
```

Download
--------
```gradle
repositories {
    maven { url "https://maven.google.com" }
    jcenter()
}

dependencies {
    compile 'com.hendraanggrian:rx-activity:0.3'
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
