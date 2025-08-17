plugins {
    alias(libs.plugins.stampify.android.application.compose)
}

android {
    namespace = "com.coderpakistan.stampify"
    defaultConfig {

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    // Splash screen
    implementation(libs.androidx.core.splashscreen)

    //Modules
    implementation(projects.core.presentation.designsystem)
    implementation(projects.design.presentation)
    implementation(projects.design.location)

    //Navigation
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.material.icons.extended)

    implementation(libs.bundles.koin)
}