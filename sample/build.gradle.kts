plugins {
    alias(libs.plugins.android.application)
    kotlin("android") version libs.versions.kotlin
}

android {
    namespace = "com.example.$RELEASE_ARTIFACT"
    testNamespace = "$namespace.test"
    defaultConfig {
        applicationId = namespace
        multiDexEnabled = true
    }
    lint.abortOnError = false
}

dependencies {
    api(project(":$RELEASE_ARTIFACT"))
    implementation(libs.material)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.multidex)
    implementation(libs.cameraview.ex) {
        exclude("com.android.support", "support-annotations")
        exclude("com.android.support", "transition")
        exclude("com.android.support", "exifinterface")
        exclude("android.arch.lifecycle", "livedata-core")
    }
}
