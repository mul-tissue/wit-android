plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
}

android{
    namespace = "com.multissue.wit.core.ui"
    compileSdk = 36
}

kotlin {
    sourceSets {
        dependencies {
            api(libs.androidx.metrics)

            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(platform(libs.androidx.compose.bom))
            implementation(libs.androidx.compose.ui)
            implementation(libs.androidx.compose.ui.graphics)
            implementation(libs.androidx.compose.material3)
        }
    }
}