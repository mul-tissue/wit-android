plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.com.multissue.wit.compose)
}

android{
    namespace = "com.multissue.wit.core.ui"
    compileSdk = 36
}

kotlin {
    sourceSets {
        dependencies {
            api(libs.androidx.metrics)
            implementation(projects.core.designsystem)

            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.androidx.lifecycle.viewmodel.ktx)

            implementation(libs.coil.kt)
            implementation(libs.coil.kt.compose)
            implementation(libs.accompanist.permissions)

            implementation(libs.maps.compose)
            implementation(libs.maps.utils)
            implementation(libs.maps.utils.ktx)
            implementation(libs.androidx.vectordrawable)
            implementation(libs.play.services.location)
        }
    }
}
