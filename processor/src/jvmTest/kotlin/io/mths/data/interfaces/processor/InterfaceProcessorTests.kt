package io.mths.data.interfaces.processor

import com.tschuchort.compiletesting.KotlinCompilation.ExitCode.OK
import com.tschuchort.compiletesting.SourceFile
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class InterfaceProcessorTests : StringSpec({
	"Compilation should succeed" {
		val sourceCode = SourceFile.kotlin("Project.kt", """
			package com.example.dtos
			import io.mths.data.interfaces.data

			@data interface Project {
				val name: String
			}
		""".trimIndent())

		compileAndCheck(sourceCode) { exitCode shouldBe OK }
	}
})
