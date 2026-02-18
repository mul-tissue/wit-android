package com.multissue.convention.bundle

import com.multissue.convention.dsl.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.Actions.with
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class AndroidFeaturePlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.multissue.wit.library")
            apply(plugin = "com.multissue.wit.kotlin")
            apply(plugin = "com.multissue.wit.compose")
            apply(plugin = "com.multissue.wit.hilt")
            apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

            dependencies {
                "api"(project(":core:navigation"))
                "implementation"(project(":core:ui"))
                "implementation"(project(":core:designsystem"))

                "implementation"(libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                "implementation"(libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
                "implementation"(libs.findLibrary("androidx.hilt.lifecycle.viewModelCompose").get())
                "implementation"(libs.findLibrary("androidx.navigation3.runtime").get())
                "implementation"(libs.findLibrary("androidx.tracing.ktx").get())
            }
        }
    }
}