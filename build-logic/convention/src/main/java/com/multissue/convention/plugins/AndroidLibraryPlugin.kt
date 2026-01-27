package com.multissue.convention.plugins

import com.multissue.convention.dsl.androidLibrary
import com.multissue.convention.dsl.configureAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
            }

            androidLibrary {
                configureAndroid()
            }
        }
    }
}