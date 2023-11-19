package io.mths.data.interfaces.processor

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.symbolProcessorProviders

fun compileAndCheck(vararg sourceFiles: SourceFile, check: KotlinCompilation.Result.() -> Unit) {
	val compilation = KotlinCompilation().apply {
		sources = sourceFiles.toList()
		symbolProcessorProviders = listOf(InterfaceProcessorProvider())
	}

	compilation.compile().check()
}
