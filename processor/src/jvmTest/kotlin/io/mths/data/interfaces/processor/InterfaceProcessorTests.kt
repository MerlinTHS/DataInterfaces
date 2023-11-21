package io.mths.data.interfaces.processor

import com.tschuchort.compiletesting.KotlinCompilation.ExitCode.COMPILATION_ERROR
import com.tschuchort.compiletesting.KotlinCompilation.ExitCode.OK
import com.tschuchort.compiletesting.SourceFile
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldEndWith

class InterfaceProcessorTests : FreeSpec({
	"Finds annotated interfaces" {
		val sourceCode = SourceFile.kotlin("Project.kt", """
			package com.example.dtos
			import io.mths.data.interfaces.data

			@data interface Project {
				val id: Long
			}
		""".trimIndent())

		compileAndCheck(sourceCode) {
			exitCode shouldBe OK
			messages shouldContain Infos.processing("com.example.dtos.Project")
			generatedKspSources.first().path shouldEndWith "GeneratedProject.kt"
		}
	}
	"Accepts local interfaces" {
		val sourceCode = SourceFile.kotlin("DTOs.kt", """
			package com.example.dtos
			import io.mths.data.interfaces.data

			object DTOs {
				@data interface Person {
					val name: String
				}
			}
		""".trimIndent())

		compileAndCheck(sourceCode) {
			exitCode shouldBe OK
			generatedKspSources.first().path shouldEndWith "DTOs.kt"
		}
	}
	"Generates data class with the same properties" {
		val sourceCode = SourceFile.kotlin("Project.kt", """
			package com.example.dtos
			import io.mths.data.interfaces.data

			@data interface Project {
				val id: Long
			}
		""".trimIndent())

		compileAndCheck(sourceCode) {
			exitCode shouldBe OK
			generatedKspSources.first().readText() shouldContain Regex("data class Project\\(\\s*public val id: Long,\\s*\\)")
		}
	}
	"Compilation fails if annotated type" - {
		"Is not an interface" {
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
		"Has no properties" {
			val sourceCode = SourceFile.kotlin("Project.kt", """
					package com.example.dtos
					import io.mths.data.interfaces.data

					@data interface EmptyThing
				""".trimIndent())

			compileAndCheck(sourceCode) {
				exitCode shouldBe COMPILATION_ERROR
				messages shouldContain Errors.noProperties("com.example.dtos.EmptyThing")
			}
		}
	}
})
