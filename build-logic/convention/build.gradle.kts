plugins {
    `kotlin-dsl`
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlin {
    jvmToolchain(17)
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.bundles.plugins)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = libs.plugins.com.multissue.wit.application.get().pluginId
            implementationClass = "com.multissue.convention.plugins.AndroidApplicationPlugin"
        }
        register("AndroidComposePlugin") {
            id = libs.plugins.com.multissue.wit.compose.get().pluginId
            implementationClass = "com.multissue.convention.plugins.AndroidComposePlugin"
        }
        register("AndroidKotlinPlugin") {
            id = libs.plugins.com.multissue.wit.kotlin.get().pluginId
            implementationClass = "com.multissue.convention.plugins.AndroidKotlinPlugin"
        }
        register("AndroidLibraryPlugin") {
            id = libs.plugins.com.multissue.wit.library.get().pluginId
            implementationClass = "com.multissue.convention.plugins.AndroidLibraryPlugin"
        }
        register("HiltPlugin") {
            id = libs.plugins.com.multissue.wit.hilt.get().pluginId
            implementationClass = "com.multissue.convention.plugins.HiltPlugin"
        }


        // Bundle -> 여러 Plugin 조합하여 사용
        register("androidFeature") {
            id = libs.plugins.com.multissue.wit.feature.get().pluginId
            implementationClass = "com.multissue.convention.bundle.AndroidFeaturePlugin"
        }
    }
}