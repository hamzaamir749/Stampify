plugins {
    alias(libs.plugins.stampify.android.feature.ui)

}

android {
    namespace = "com.coderpakistan.design.presentation"
}

dependencies {

    implementation(libs.androidx.activity.compose)
    implementation(projects.core.domain)
    implementation(projects.design.domain)
}