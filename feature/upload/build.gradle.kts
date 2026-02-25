android.namespace = "com.multissue.wit.feature.upload"

plugins {
    alias(libs.plugins.com.multissue.wit.feature)
}

kotlin {
    sourceSets {
        dependencies {
            implementation(libs.androidx.compose.material3)
            implementation(libs.androidx.activity.compose)
            implementation(libs.play.services.location)
            implementation(libs.accompanist.permissions)

            implementation(libs.coil.kt)
            implementation(libs.coil.kt.compose)

            implementation("androidx.camera:camera-core:1.5.3")
            implementation("androidx.camera:camera-camera2:1.5.3")
            implementation("androidx.camera:camera-lifecycle:1.5.3")
            implementation("androidx.camera:camera-view:1.5.3")
        }
    }
}