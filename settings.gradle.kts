rootProject.name = "wit"

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core:designsystem")
include(":core:network")
include(":core:domain")
include(":core:navigation")
include(":core:ui")
include(":core:datastore")
include(":feature:login")
include(":feature:onboarding")
include(":feature:signup")
include(":feature:chat")
include(":feature:mypage")
include(":feature:home")
include(":feature:map")
include(":feature:upload")
include(":feature:feed")
