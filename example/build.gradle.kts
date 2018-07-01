import org.gradle.kotlin.dsl.kotlin

plugins {
    `android-application`
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

repositories {
    maven("https://dl.bintray.com/hendraanggrian/bundler")
}

dependencies {
    implementation(project(":$RELEASE_ARTIFACT"))
    implementation(project(":cameraview:library"))
    implementation(kotlin("stdlib", VERSION_KOTLIN))

    implementation(support("preference-v7", VERSION_SUPPORT))
    implementation(support("appcompat-v7", VERSION_SUPPORT))
    implementation(support("design", VERSION_SUPPORT))

    implementation(rxJava2("rxjava"))
    implementation(photoView())
    implementation(processPhoenix())
    debugImplementation(leakCanary())
    releaseImplementation(leakCanary("no-op"))

    implementation(hendraanggrian("preferencer", VERSION_PREFERENCER))
    kapt(hendraanggrian("preferencer", VERSION_PREFERENCER, "compiler"))

    implementation(hendraanggrian("bundler", VERSION_BUNDLER))
    kapt(hendraanggrian("bundler", VERSION_BUNDLER, "compiler"))
}
