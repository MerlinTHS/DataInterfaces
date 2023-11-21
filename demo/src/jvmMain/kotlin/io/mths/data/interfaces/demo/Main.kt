package io.mths.data.interfaces.demo

import io.mths.data.interfaces.demo.generated.Product

fun main() {
	val book = Product(id = 1, name = "Book", price = 9_99L)
	println("Look at this $book")
}
