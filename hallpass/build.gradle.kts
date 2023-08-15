import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.android.library)
    kotlin("android")
    alias(libs.plugins.maven.publish)
}

android {
    namespace = "$RELEASE_GROUP.$RELEASE_ARTIFACT"
    testNamespace = "$namespace.test"
    buildFeatures.buildConfig = false
    testOptions.unitTests.isIncludeAndroidResources = true
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.S01)
    signAllPublications()
    pom(::configurePom)
    configure(AndroidSingleVariantLibrary())
}

dependencies {
    ktlint(libs.ktlint, ::configureKtlint)
    ktlint(libs.rulebook.ktlint)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment)
    testImplementation(libs.bundles.androidx.test)
}
