plugins {
    alias(libs.plugins.com.multissue.wit.feature)
}

android.namespace = "com.multissue.wit.feature.chat"

kotlin {
    sourceSets {
        dependencies {
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.androidx.lifecycle.viewModelCompose)
            implementation(libs.androidx.hilt.lifecycle.viewModelCompose)
            implementation(libs.androidx.navigation3.runtime)
//            "implementation"(libs.findLibrary("androidx.tracing.ktx").get())

            implementation(libs.androidx.compose.material3)
        }
    }
}