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
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MusicApplication"
include(":app")
include(":core-ui")
include(":core-data")
include(":navigation")
include(":service")
include(":feature-home")
include(":feature-explore")
include(":feature-saved")
include(":feature-detail")
include(":feature-home:impl")
include(":feature-home:api")
include(":feature-explore:api")
include(":feature-explore:impl")
include(":feature-saved:api")
include(":feature-saved:impl")
include(":feature-detail:api")
include(":feature-detail:impl")
include(":core-sync")
include(":feature-detail:ui")
include(":feature-explore:ui")
include(":feature-home:ui")
include(":feature-saved:ui")
