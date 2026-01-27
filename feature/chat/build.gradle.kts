plugins {
    alias(libs.plugins.com.multissue.wit.feature)
}

android.namespace = "com.multissue.wit.feature.chat"

kotlin {
    sourceSets {
        dependencies {
            implementation(libs.androidx.compose.material3)
        }
    }
}