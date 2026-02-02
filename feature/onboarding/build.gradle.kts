android.namespace = "com.multissue.wit.feature.onboarding"

plugins {
    alias(libs.plugins.com.multissue.wit.feature)
}

kotlin {
    sourceSets {
        dependencies {
            implementation(projects.feature.login)
            implementation(libs.androidx.compose.material3)
        }
    }
}