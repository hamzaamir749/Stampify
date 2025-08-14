plugins {
    alias(libs.plugins.stampify.android.library.compose)
}

android {
    namespace = "com.coderpakistan.core.presentation.ui"
}

dependencies {

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(projects.core.domain)
    implementation(projects.core.presentation.designsystem)
}