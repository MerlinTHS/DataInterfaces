package io.mths.data.interfaces.processor

import com.google.devtools.ksp.symbol.KSClassDeclaration

/**
 * Returns the qualified name of the class symbol
 * or an empty string if it's not available.
 */
val KSClassDeclaration.nameOrEmpty: String get() =
	qualifiedName?.asString().orEmpty()
