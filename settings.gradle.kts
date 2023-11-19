rootProject.name = "DataInterfaces"
include("annotations")
include("processor")

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories { mavenCentral() }
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
