plugins {
    alias(libs.plugins.stampify.android.library)
}
android {
    namespace = "com.coderpakistan.design.data"
}

dependencies {

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.google.android.gms.play.services.location)
    implementation(libs.androidx.work)
    implementation(libs.koin.android.workmanager)
    implementation(projects.core.domain)
    implementation(projects.design.domain)

}