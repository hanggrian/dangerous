buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", VERSION_KOTLIN))
        classpath(android())
        classpath(dokka())
        classpath(gitPublish())
        classpath(bintrayRelease())
    }
}

// submodule cameraview config
ext["buildToolsVersion"] = "28.0.1"
ext["compileSdkVersion"] = SDK_TARGET
ext["minSdkVersion"] = SDK_MIN
ext["targetSdkVersion"] = SDK_TARGET
ext["supportLibraryVersion"] = "28.0.0"

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://jitpack.io")
    }
    tasks.withType<Delete> {
        delete(projectDir.resolve("out"))
    }
}

tasks.register<Delete>("clean") {
    delete(buildDir)
}