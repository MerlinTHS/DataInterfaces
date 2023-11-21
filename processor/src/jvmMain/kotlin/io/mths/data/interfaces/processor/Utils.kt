package io.mths.data.interfaces.processor

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSFile

/**
 * Returns the qualified name of the class symbol
 * or an empty string if it's not available.
 */
val KSClassDeclaration.nameOrEmpty: String get() =
	qualifiedName?.asString().orEmpty()

/**
 * Returns the file that contains the given declaration.
 *
 * If the given one is not a top-level declaration,
 * it recursively calls topLevelFile on the parent declaration.
 */
val KSDeclaration.topLevelFile: KSFile get() =
	containingFile ?: parentDeclaration!!.topLevelFile
