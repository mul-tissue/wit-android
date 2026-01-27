package com.multissue.convention.plugins

import com.multissue.convention.dsl.android
import com.multissue.convention.dsl.androidApplication
import com.multissue.convention.dsl.libs
import com.multissue.convention.dsl.version
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
            }

            androidApplication {
                android {
                    namespace?.let { this.namespace = it }
                    compileSdk { version = release(libs.version("compileSdk").toInt()) }
                    defaultConfig {
                        applicationId = libs.version("applicationId")
                        minSdk = libs.version("minSdk").toInt()
                        targetSdk = libs.version("targetSdk").toInt()
                        versionCode = libs.version("versionCode").toInt()
                        versionName = libs.version("versionName")
                    }
                    buildFeatures {
                        compose = true
                    }
                    composeOptions {
                        kotlinCompilerExtensionVersion = libs.version("compose")
                    }
//                    packaging {
//                        resources {
//                            excludes +=
//                                listOf(
//                                    "/META-INF/{AL2.0,LGPL2.1}",
//                                    "META-INF/INDEX.LIST",
//                                )
//                        }
//                    }
                    buildTypes {
                        getByName("release") {
                            isMinifyEnabled = false
                        }
                    }
                    compileOptions {
                        sourceCompatibility = JavaVersion.VERSION_17
                        targetCompatibility = JavaVersion.VERSION_17
                    }
                }

                testOptions {
                    unitTests {
                        isIncludeAndroidResources = true
                    }
                }
            }
        }
    }
}
