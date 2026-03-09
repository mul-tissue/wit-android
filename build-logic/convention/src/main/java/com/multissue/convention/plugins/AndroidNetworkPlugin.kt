package com.multissue.convention.plugins

import com.multissue.convention.dsl.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class AndroidNetworkPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.multissue.wit.library")
            apply(plugin = "com.multissue.wit.kotlin")
            apply(plugin = "com.multissue.wit.hilt")
            apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

            dependencies {
                "implementation"(libs.findBundle("network").get())
            }
        }
    }
}
