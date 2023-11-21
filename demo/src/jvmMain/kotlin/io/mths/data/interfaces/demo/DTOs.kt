package io.mths.data.interfaces.demo

import io.mths.data.interfaces.data

@data private interface Product : ProductData {
	val id: Long
}

@data private interface ProductData {
	val name: String
	val price: Long
}
