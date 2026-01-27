package com.multissue.convention.bundle

import com.multissue.convention.dsl.kotlin
import com.multissue.convention.dsl.libs
import com.multissue.convention.dsl.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.Actions.with
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke

class AndroidFeaturePlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
//                apply(libs.plugin("com.multissue.wit.hilt").pluginId)
                apply(libs.plugin("com.multissue.wit.library").pluginId)
                apply(libs.plugin("com.multissue.wit.kotlin").pluginId)
                apply(libs.plugin("com.multissue.wit.compose").pluginId)

                apply(libs.plugin("retrofit-kotlin-serialization").pluginId)
            }

            kotlin {
                sourceSets {
                    dependencies {
                        "api"(project(":core:navigation"))
                    }
                }
            }
        }

    }
}