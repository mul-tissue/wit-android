android.namespace = "com.multissue.wit.feature.login"

plugins {
    alias(libs.plugins.com.multissue.wit.feature)
}

kotlin {
    sourceSets {
        dependencies {
            implementation(libs.androidx.compose.material3)
            implementation(projects.core.domain)
            implementation(libs.kakao.user)
        }
    }
}
