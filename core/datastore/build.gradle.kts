plugins {
    alias(libs.plugins.com.multissue.wit.library)
    alias(libs.plugins.com.multissue.wit.compose)
    alias(libs.plugins.com.multissue.wit.hilt)
}

android {
    namespace = "com.multissue.wit.core.datastore"

    defaultConfig {
        testInstrumentationRunner = "com.multissue.wit.core.datastore.HiltTestRunner"
    }
}

dependencies {
    implementation(libs.datastore)
    implementation(libs.datastore.preferences)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    androidTestImplementation(libs.androidx.runner)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.hilt.android.test)
}