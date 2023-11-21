package io.mths.data.interfaces.processor

import com.tschuchort.compiletesting.KotlinCompilation.Result
import java.io.File

/**
 * Returns a sequence of all generated sources by
 * KSP annotation processors.
 *
 * This method is a temporary workaround and should be
 * removed as soon as Kotlin-Compile-Testing includes
 * generated KSP files.
 *
 * See [Issue 129](https://github.com/tschuchortdev/kotlin-compile-testing/issues/129)
 */
val Result.generatedKspSources get() =
	workingDir.resolve("ksp/sources")
		.run { resolve("kotlin").walk() + resolve("java").walk() }
		.filter { it.isFile }

private val Result.workingDir: File get() =
	outputDirectory.parentFile!!
