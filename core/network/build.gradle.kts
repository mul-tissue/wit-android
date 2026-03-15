plugins {
    alias(libs.plugins.com.multissue.wit.network)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.multissue.wit.core.network"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}