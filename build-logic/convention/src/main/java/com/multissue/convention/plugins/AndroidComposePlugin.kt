package com.multissue.convention.plugins

import com.multissue.convention.dsl.android
import com.multissue.convention.dsl.implementation
import com.multissue.convention.dsl.implementationPlatform
import com.multissue.convention.dsl.kotlin
import com.multissue.convention.dsl.library
import com.multissue.convention.dsl.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke

class AndroidComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.plugin.compose")
            }
            if (plugins.hasPlugin("com.android.library")) {
                android {
                    buildFeatures.compose = true
                }
            }
            kotlin {
                sourceSets {
                    dependencies {
                        implementationPlatform(libs.library("androidx-compose-bom"))
                        implementation(libs.library("androidx-compose-ui"))
                        implementation(libs.library("androidx-compose-ui-graphics"))
                        implementation(libs.library("androidx-compose-ui-tooling"))
                        implementation(libs.library("androidx-compose-ui-tooling-preview"))
                        implementation(libs.library("androidx-compose-ui-test-manifest"))
                        implementation(libs.library("androidx-compose-ui-test-junit4"))
                        implementation(libs.library("androidx-compose-material3"))
                        implementation(libs.library("androidx-compose-runtime"))
                    }
                }
            }
        }
    }
}
