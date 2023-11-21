rootProject.name = "DataInterfaces"
include(":annotations")
include(":processor")
include(":demo")

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories { mavenCentral() }
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    versionCatalogs { libs; testLibs }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

val MutableVersionCatalogContainer.libs get() = create("libs") {
    library("ksp", "com.google.devtools.ksp", "symbol-processing-api").version("1.9.20-1.0.14")

    version("kotlinpoet", "1.15.1").also {
        library("kotlinpoet", "com.squareup", "kotlinpoet").versionRef(it)
        library("kotlinpoet-ksp", "com.squareup", "kotlinpoet-ksp").versionRef(it)
    }

    bundle("kotlinpoet", listOf("kotlinpoet", "kotlinpoet-ksp"))
}

val MutableVersionCatalogContainer.testLibs get() = create("testLibs") {
	library("compile-testing", "com.github.tschuchortdev", "kotlin-compile-testing-ksp").version("1.5.0")

	version("kotest", "5.8.0").also {
		library("kotest-runner", "io.kotest", "kotest-runner-junit5").versionRef(it)
		library("kotest-assertions", "io.kotest", "kotest-assertions-core").versionRef(it)
	}

	bundle("kotest", listOf("kotest-runner", "kotest-assertions"))
}
