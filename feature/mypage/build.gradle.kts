android.namespace = "com.multissue.wit.feature.mypage"

plugins {
    alias(libs.plugins.com.multissue.wit.feature)
}

kotlin {
    sourceSets {
        dependencies {
            implementation(libs.androidx.compose.material3)
            implementation(libs.coil.kt)
            implementation(libs.coil.kt.compose)
            implementation(libs.androidx.activity.compose)

            implementation(libs.maps.compose)
            implementation(libs.maps.utils)
            implementation(libs.maps.utils.ktx)
            implementation(libs.androidx.vectordrawable)
            implementation(libs.play.services.location)
        }
    }
}