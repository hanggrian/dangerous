private const val VERSION_LEAKCANARY = "1.5.4"

fun Dependencies.leakCanary(module: String? = null) = "com.squareup.leakcanary:${module?.let { "leakcanary-android-$it" }
    ?: "leakcanary-android"}:$VERSION_LEAKCANARY"