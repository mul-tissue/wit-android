package com.multissue.convention.plugins

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.multissue.convention.dsl.androidLibrary
import com.multissue.convention.dsl.configureAndroid
import com.multissue.convention.extensions.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.library")

            extensions.configure<LibraryExtension> {
                compileSdk = 36
            }
            androidLibrary {
//                configureAndroid()
            }
        }
    }
}