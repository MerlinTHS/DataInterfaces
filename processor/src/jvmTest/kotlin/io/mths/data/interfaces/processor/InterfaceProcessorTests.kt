package io.mths.data.interfaces.processor

import com.tschuchort.compiletesting.KotlinCompilation.ExitCode.COMPILATION_ERROR
import com.tschuchort.compiletesting.KotlinCompilation.ExitCode.OK
import com.tschuchort.compiletesting.SourceFile
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

class InterfaceProcessorTests : StringSpec({
	"Finds annotated interface" {
		val sourceCode = SourceFile.kotlin("Project.kt", """
			package com.example.dtos
			import io.mths.data.interfaces.data

			@data interface Project
		""".trimIndent())

		compileAndCheck(sourceCode) {
			exitCode shouldBe OK
			messages shouldContain Infos.processing("com.example.dtos.Project")
		}
	}
	"Compilation fails if @data annotates non-interface type" {
		val sourceCode = SourceFile.kotlin("Project.kt", """
			package com.example.dtos
			import io.mths.data.interfaces.data

			@data class Class
			@data object Singleton
			@data annotation class Annotation
			@data enum class Enum {
				@data Entry
			}
		""".trimIndent())

		compileAndCheck(sourceCode) {
			exitCode shouldBe COMPILATION_ERROR

			listOf("Class", "Annotation", "Singleton", "Enum", "Enum.Entry")
				.map { "com.example.dtos.$it" }
				.forEach { type -> messages shouldContain Errors.noInterface(type) }
		}
	}
})
