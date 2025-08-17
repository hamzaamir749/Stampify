plugins {
    alias(libs.plugins.stampify.android.feature.ui)

}

android {
    namespace = "com.coderpakistan.design.presentation"
}

dependencies {

    implementation(libs.androidx.activity.compose)
    implementation(libs.bundles.camera)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.google.android.gms.play.services.location)
    implementation(projects.core.domain)
    implementation(projects.design.domain)
}