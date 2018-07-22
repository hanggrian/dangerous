import com.android.build.gradle.ProguardFiles.getDefaultProguardFile
import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    android("application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(SDK_TARGET)
    buildToolsVersion(BUILD_TOOLS)
    defaultConfig {
        minSdkVersion(SDK_MIN)
        targetSdkVersion(SDK_TARGET)
        applicationId = "com.example.$RELEASE_ARTIFACT"
        versionCode = 1
        versionName = RELEASE_VERSION
    }
    sourceSets {
        getByName("main") {
            manifest.srcFile("AndroidManifest.xml")
            java.srcDirs("src")
            res.srcDir("res")
            resources.srcDir("src")
        }
    }
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    lintOptions {
        isAbortOnError = false
    }
}

dependencies {
    implementation(project(":$RELEASE_ARTIFACT"))
    implementation(project(":cameraview:library"))
    implementation(kotlin("stdlib", VERSION_KOTLIN))

    implementation(androidx("preference"))
    implementation(androidx("appcompat"))
    implementation(material())

    implementation(anko("commons"))

    implementation(rxJava2("rxjava"))
    implementation(photoView())
    implementation(processPhoenix())
    debugImplementation(leakCanary())
    releaseImplementation(leakCanary("no-op"))

    implementation(hendraanggrian("preferencer", version = VERSION_PREFERENCER))
    kapt(hendraanggrian("preferencer", "preferencer-compiler", VERSION_PREFERENCER))

    implementation(hendraanggrian("bundler", version = VERSION_BUNDLER))
    kapt(hendraanggrian("bundler", "bundler-compiler", VERSION_BUNDLER))
}
