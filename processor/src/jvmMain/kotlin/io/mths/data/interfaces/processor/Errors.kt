package io.mths.data.interfaces.processor

object Errors {
	fun noInterface(qualifiedName: String) =
		"$qualifiedName needs to be an interface to be annotated with @data"
}
