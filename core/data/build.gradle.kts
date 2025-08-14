plugins {
    alias(libs.plugins.stampify.android.library)
}

android {
    namespace = "com.coderpakistan.core.data"
}

dependencies {
    implementation(libs.bundles.koin)
    implementation(projects.core.domain)
}