plugins {
    `kotlin-dsl`
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlin {
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
        register("androidApplicationCompose") {
            id = libs.plugins.com.multissue.wit.application.get().pluginId
            implementationClass = "com.multissue.convention.plugins.AndroidApplicationComposePlugin"
        }
        register("androidLibraryCompose") {
            id = libs.plugins.com.multissue.wit.application.get().pluginId
            implementationClass = "com.multissue.convention.plugins.AndroidLibraryComposePlugin"
        }
    }
}