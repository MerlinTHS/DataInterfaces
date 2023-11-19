package io.mths.data.interfaces

/**
 * Marks an interface as a data interface which will be used
 * to generate the corresponding data classes.
 */
@Suppress("ClassName")
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class data