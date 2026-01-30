android.namespace = "com.multissue.wit.feature.home"

plugins {
    alias(libs.plugins.com.multissue.wit.feature)
}

kotlin {
    sourceSets {
        dependencies {
            implementation(libs.androidx.compose.material3)
        }
    }
}