plugins {
    `kotlin-dsl`
}
group = "com.coderpakistan.stampify.buildlogic"
dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "stampify.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "stampify.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "stampify.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "stampify.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("jvmLibrary") {
            id = "stampify.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("androidFeatureUi") {
            id = "stampify.android.feature.ui"
            implementationClass = "AndroidFeatureUiConventionPlugin"
        }

    }
}