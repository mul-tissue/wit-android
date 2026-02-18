android.namespace = "com.multissue.wit.feature.signup"

plugins {
    alias(libs.plugins.com.multissue.wit.feature)
}

kotlin {
    sourceSets {
        dependencies {
            implementation(libs.androidx.compose.material3)
            implementation(libs.kotlinx.datetime)
        }
    }
}