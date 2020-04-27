plugins {
    android("application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(SDK_TARGET)
    defaultConfig {
        minSdkVersion(SDK_MIN)
        targetSdkVersion(SDK_TARGET)
        applicationId = "com.example.$RELEASE_ARTIFACT"
        versionName = RELEASE_VERSION
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    sourceSets {
        getByName("main") {
            manifest.srcFile("AndroidManifest.xml")
            java.srcDir("src")
            res.srcDir("res")
            resources.srcDir("src")
        }
    }
    applicationVariants.all {
        generateBuildConfigProvider?.configure {
            enabled = false
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
    packagingOptions {
        exclude("META-INF/services/javax.annotation.processing.Processor")
    }
    lintOptions {
        isAbortOnError = false
    }
}

dependencies {
    api(kotlin("stdlib", VERSION_KOTLIN))

    implementation(project(":$RELEASE_ARTIFACT"))

    implementation(material())
    implementation(androidx("multidex", version = VERSION_MULTIDEX))
    implementation(androidx("core", "core-ktx"))
    implementation(androidx("appcompat"))
    implementation(androidx("preference", "preference-ktx"))
    implementation(androidx("coordinatorlayout"))
    implementation(androidx("recyclerview"))

    implementation(hendraanggrian("material", "bannerbar-ktx", VERSION_ANDROIDX))
    implementation(hendraanggrian("prefy", "prefy-android", VERSION_PREFY))
    kapt(hendraanggrian("prefy", "prefy-compiler", VERSION_PREFY))
    implementation(processPhoenix())

    debugImplementation(leakCanary())
}
