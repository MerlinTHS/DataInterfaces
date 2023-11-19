package io.mths.data.interfaces.processor

import com.tschuchort.compiletesting.*

fun compileAndCheck(vararg sourceFiles: SourceFile, check: KotlinCompilation.Result.() -> Unit) {
	val compilation = KotlinCompilation().apply {
		sources = sourceFiles.toList()
		kspIncremental = true
		kspIncrementalLog = true
		inheritClassPath = true
		kspWithCompilation = true
		symbolProcessorProviders = listOf(InterfaceProcessorProvider())
	}

	compilation.compile().check()
}
