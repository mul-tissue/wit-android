android.namespace = "com.multissue.wit.feature.feed"

plugins {
    alias(libs.plugins.com.multissue.wit.feature)
}

kotlin {
    sourceSets {
        dependencies {
            implementation(libs.androidx.compose.material3)
            implementation(libs.coil.kt)
            implementation(libs.coil.kt.compose)

            implementation(libs.accompanist.permissions)
        }
    }
}