import java.util.Properties

android.namespace = "com.multissue.wit.feature.login"

plugins {
    alias(libs.plugins.com.multissue.wit.feature)
}

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) load(file.inputStream())
}

android {
    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        buildConfigField(
            "String",
            "GOOGLE_CLIENT_ID",
            "\"${localProperties.getProperty("GOOGLE_CLIENT_ID", "")}\""
        )
    }
}

kotlin {
    sourceSets {
        dependencies {
            implementation(libs.androidx.compose.material3)
            implementation(projects.core.domain)
            implementation(libs.kakao.user)
            implementation(libs.credentials)
            implementation(libs.credentials.play.services)
            implementation(libs.googleid)
        }
    }
}
