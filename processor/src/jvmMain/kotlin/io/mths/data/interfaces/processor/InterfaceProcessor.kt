package io.mths.data.interfaces.processor

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ksp.writeTo
import io.mths.data.interfaces.data

class InterfaceProcessor(
	private val logger: KSPLogger,
	private val generator: CodeGenerator
) : SymbolProcessor {
	private val builderByFile = mutableMapOf<KSFile, FileSpec.Builder>()

	/**
	 * Generates data classes for annotated symbols that are
	 * transformable (see isTransformable).
	 */
	override fun process(resolver: Resolver): List<KSAnnotated> {
		return resolver
			.getSymbolsWithAnnotation(data::class.qualifiedName!!)
			.onEachValid { if (it.isTransformable) generateDataClass(it) }
	}

	/**
	 * Writes the collected declarations into their file.
	 */
	override fun finish() {
		for ((file, builder) in builderByFile)
			builder.build().writeTo(generator, Dependencies(aggregating = true, file))
	}

	/**
	 * Returns whether the given class declaration can be transformed
	 * into a data class.
	 *
	 * Since error logs do not short-circuit, the returned value is
	 * used to determine whether to transform the given symbol
	 * into a data class. Otherwise, the generation process would also
	 * run for invalid symbols.
	 *
	 * It not, it logs the error.
	 */
	private val KSClassDeclaration.isTransformable: Boolean get() = when {
		classKind != ClassKind.INTERFACE ->
			Errors.noInterface(nameOrEmpty)

		getAllProperties().toList().isEmpty() ->
			Errors.noProperties(nameOrEmpty)

		else -> null
	}?.also { logger.error(it) } == null

	private fun generateDataClass(clazz: KSClassDeclaration) {
		logger.info(Infos.processing(clazz.nameOrEmpty))

		builderByFile.getOrPutBuilder(clazz)
			.addType(clazz.dataClassSpec)
	}
}

/**
 * Returns the FileSpec of the containing file for the given class declaration
 * or creates a new one if it does not exist.
 */
private fun MutableMap<KSFile, FileSpec.Builder>.getOrPutBuilder(clazz: KSClassDeclaration): FileSpec.Builder {
	val file = clazz.topLevelFile
	val filename = file.fileName.substringBefore(".")
	val packageName = clazz.packageName.asString()

	return getOrPut(file) {
		FileSpec.builder(
			packageName = "$packageName.generated",
			fileName = "Generated$filename"
		)
	}
}

/**
 * Executes the given action for each valid symbol of type KSClassDeclaration and
 * returns the invalid symbols for the next processing round.
 *
 * See [Multiple Round Processing](https://kotlinlang.org/docs/ksp-multi-round.html)
 */
fun Sequence<KSAnnotated>.onEachValid(action: (KSClassDeclaration) -> Unit): List<KSAnnotated> =
	groupBy { it.validate() }.also {
		it[true]?.filterIsInstance<KSClassDeclaration>()?.forEach(action)
	}[false].orEmpty()
