package com.multissue.convention.plugins

import com.multissue.convention.dsl.android
import com.multissue.convention.dsl.implementation
import com.multissue.convention.dsl.kotlin
import com.multissue.convention.dsl.kotlinAndroidOptions
import com.multissue.convention.dsl.library
import com.multissue.convention.dsl.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidKotlinPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.serialization")
            }
            tasks.withType(KotlinCompile::class.java) {
                compilerOptions {
                    // Treat all Kotlin warnings as errors (disabled by default)
                    allWarningsAsErrors.set(properties["warningsAsErrors"] as? Boolean ?: false)
                    freeCompilerArgs.addAll(
                        listOf(
                            "-opt-in=kotlin.RequiresOptIn",
                        ),
                    )
                    jvmTarget.set(JvmTarget.JVM_17)
                }
            }

            kotlinAndroidOptions {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                }
            }
            dependencies {
                implementation(libs.library("kotlinx-coroutines-core"))
            }
        }
    }
}
