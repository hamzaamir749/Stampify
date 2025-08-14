import com.coderpakistan.convention.addUiLayerDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import kotlin.run

class AndroidFeatureUiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("stampify.android.library.compose")
            }

            dependencies {
                addUiLayerDependencies(target)
            }
        }
    }
}

