plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.multissue.wit.core.navigation"
    compileSdk = 36
}

kotlin {
    sourceSets {
        dependencies {
            api(libs.androidx.navigation3.runtime)
            implementation(libs.androidx.savedstate.compose)
            implementation(libs.androidx.lifecycle.viewModel.navigation3)
        }
    }
}