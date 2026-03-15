android.namespace = "com.multissue.wit.core.data"

plugins {
    alias(libs.plugins.com.multissue.wit.library)
    alias(libs.plugins.com.multissue.wit.hilt)
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.network)
    implementation(projects.core.datastore)
}
