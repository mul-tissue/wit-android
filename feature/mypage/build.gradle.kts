plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)
}

android{
    namespace = "com.multissue.wit.feature.mypage"
    compileSdk = 36
}

kotlin {
    sourceSets {
        dependencies {
            api(project(":core:navigation"))
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.androidx.lifecycle.viewModelCompose)
            implementation(libs.androidx.hilt.lifecycle.viewModelCompose)
            implementation(libs.androidx.navigation3.runtime)
//            "implementation"(libs.findLibrary("androidx.tracing.ktx").get())

            implementation(platform(libs.androidx.compose.bom))
            implementation(libs.androidx.compose.ui)
            implementation(libs.androidx.compose.ui.graphics)
            implementation(libs.androidx.compose.ui.tooling.preview)
            implementation(libs.androidx.compose.material3)
        }
    }
}