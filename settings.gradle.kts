pluginManagement {
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

rootProject.name = "wit"
include(":app")
include(":build-logic:convention")
include(":core:designsystem")
include(":core:network")
include(":core:domain")
include(":core:navigation")
include(":feature:login")
include(":feature:onboarding")
include(":feature:signup")
include(":feature:chat")
include(":feature:mypage")
include(":feature:home")
include(":feature:map")
include(":core:ui")
