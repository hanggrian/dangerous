buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(android())
        classpath(kotlin("gradle-plugin", VERSION_KOTLIN))
        classpath(dokka())
        classpath(gitPublish())
        classpath(bintrayRelease())
    }
}

// submodule cameraview config
ext["buildToolsVersion"] = BUILD_TOOLS
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
    tasks.withType<Javadoc> {
        isEnabled = false
    }
}

tasks {
    "clean"(Delete::class) {
        delete(buildDir)
    }
    "wrapper"(Wrapper::class) {
        gradleVersion = VERSION_GRADLE
    }
}