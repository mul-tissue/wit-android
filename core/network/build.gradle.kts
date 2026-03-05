android.namespace = "com.multissue.wit.core.network"

plugins {
    alias(libs.plugins.com.multissue.wit.network)
}

android {
    buildFeatures {
        buildConfig = true
    }
    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://dev.api.example.com/\"")
        }
        release {
            buildConfigField("String", "BASE_URL", "\"https://api.example.com/\"")
        }
    }
}
