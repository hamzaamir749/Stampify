plugins {
    alias(libs.plugins.stampify.android.library)
}

android {
    namespace = "com.coderpakistan.design.location"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.google.android.gms.play.services.location)
    implementation(libs.bundles.koin)
    implementation(projects.core.domain)
    implementation(projects.design.domain)

}