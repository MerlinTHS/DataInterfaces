package io.mths.data.interfaces.processor

object Errors {
	fun noInterface(qualifiedName: String) =
		"$qualifiedName needs to be an interface to be annotated with @data"
	fun noProperties(qualifiedName: String) =
		"$qualifiedName needs to have at least one property to be used as a data class"
}
