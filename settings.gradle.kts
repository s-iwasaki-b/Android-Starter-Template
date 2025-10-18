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

rootProject.name = "Android-Starter-Template"
include(":app")
include(":module:base")
include(":module:core")
include(":module:data:repository")
include(":module:domain:service")
include(":module:feature:home")
include(":module:ui")
include(":module:mylibrary")
include(":mylibrary")
