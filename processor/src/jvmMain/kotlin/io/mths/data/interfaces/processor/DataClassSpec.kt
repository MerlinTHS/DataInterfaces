package io.mths.data.interfaces.processor

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName

internal val KSClassDeclaration.dataClassSpec get() = TypeSpec
	.classBuilder(toClassName())
	.addModifiers(KModifier.DATA)
	.primaryConstructor(constructorSpec)
	.addProperties(propertySpecs)
	.build()

private val KSClassDeclaration.constructorSpec get() = FunSpec
	.constructorBuilder()
	.addParameters(parameterSpecs)
	.build()

private val KSClassDeclaration.parameterSpecs get() = getAllProperties().map {
	ParameterSpec
		.builder(it.simpleName.asString(), it.type.toTypeName())
		.build()
}.toList()

private val KSClassDeclaration.propertySpecs get() = getAllProperties().map {
	val name = it.simpleName.asString()

	PropertySpec
		.builder(name, it.type.toTypeName())
		.initializer(name)
		.build()
}.toList()
