android.namespace = "com.multissue.wit.feature.map"

plugins {
    alias(libs.plugins.com.multissue.wit.feature)
}

kotlin {
    sourceSets {
        dependencies {
            implementation(projects.feature.feed)

            implementation(libs.androidx.compose.material3)

            implementation("com.google.maps.android:maps-compose:4.4.1")
            implementation(libs.accompanist.permissions)
            implementation(libs.coil.kt)
            implementation(libs.coil.kt.compose)
        }
    }
}
dependencies {
    implementation(libs.androidx.vectordrawable)
    implementation(libs.play.services.location)
}
