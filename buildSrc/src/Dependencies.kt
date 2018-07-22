import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.plugin.use.PluginDependenciesSpec

fun DependencyHandler.android() = "com.android.tools.build:gradle:$VERSION_ANDROID_PLUGIN"
fun PluginDependenciesSpec.android(submodule: String) = id("com.android.$submodule")

fun DependencyHandler.androidx(
    repository: String,
    module: String = repository,
    version: String = VERSION_ANDROIDX
): String = "androidx.$repository:$module:$version"

fun DependencyHandler.hendraanggrian(
    repository: String,
    module: String = repository,
    version: String
): String = "com.hendraanggrian.$repository:$module:$version"

fun DependencyHandler.material() = "com.google.android.material:material:$VERSION_ANDROIDX"

const val GROUP_SUPPORT = "com.android.support"

fun DependencyHandler.rxJava2(module: String) = "io.reactivex.rxjava2:$module:$VERSION_RXJAVA2"

fun DependencyHandler.photoView() = "com.github.chrisbanes:PhotoView:$VERSION_PHOTOVIEW"

fun DependencyHandler.processPhoenix() = "com.jakewharton:process-phoenix:$VERSION_PROCESSPHOENIX"

fun DependencyHandler.leakCanary(module: String? = null) = "com.squareup.leakcanary:${module?.let { "leakcanary-android-$it" }
    ?: "leakcanary-android"}:$VERSION_LEAKCANARY"

fun DependencyHandler.junit() = "junit:junit:$VERSION_JUNIT"

fun DependencyHandler.ktlint() = "com.github.shyiko:ktlint:$VERSION_KTLINT"

fun DependencyHandler.anko(module: String? = null) = "org.jetbrains.anko:${module?.let { "anko-$it" }
    ?: "anko"}:$VERSION_ANKO"

fun DependencyHandler.dokka() = "org.jetbrains.dokka:dokka-android-gradle-plugin:$VERSION_DOKKA"
inline val PluginDependenciesSpec.dokka get() = id("org.jetbrains.dokka-android")

fun DependencyHandler.gitPublish() = "org.ajoberstar:gradle-git-publish:$VERSION_GIT_PUBLISH"
inline val PluginDependenciesSpec.`git-publish` get() = id("org.ajoberstar.git-publish")

fun DependencyHandler.bintrayRelease() = "com.novoda:bintray-release:$VERSION_BINTRAY_RELEASE"
inline val PluginDependenciesSpec.`bintray-release` get() = id("com.novoda.bintray-release")
