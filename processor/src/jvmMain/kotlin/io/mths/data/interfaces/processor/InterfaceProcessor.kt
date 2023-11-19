package io.mths.data.interfaces.processor

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate
import io.mths.data.interfaces.data

class InterfaceProcessor(
	private val logger: KSPLogger
) : SymbolProcessor {
	override fun process(resolver: Resolver): List<KSAnnotated> {
		return resolver
			.getSymbolsWithAnnotation(data::class.qualifiedName!!)
			.onEachValid { logger.info("Processing ${it.qualifiedName!!.asString()}") }
	}
}

/**
 * Executes the given action for each valid node of type KSClassDeclaration and
 * returns the invalid nodes for the next processing round.
 *
 * See [Multiple Round Processing](https://kotlinlang.org/docs/ksp-multi-round.html)
 */
fun Sequence<KSAnnotated>.onEachValid(action: (KSClassDeclaration) -> Unit): List<KSAnnotated> =
	groupBy { it.validate() }.also {
		it[true]?.filterIsInstance<KSClassDeclaration>()?.forEach(action)
	}[false].orEmpty()
