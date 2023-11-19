package io.mths.data.interfaces.processor

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

			@data interface Project {
				val name: String
			}
		""".trimIndent())

		compileAndCheck(sourceCode) {
			exitCode shouldBe OK
			messages shouldContain "Processing com.example.dtos.Project"
		}
	}
})
