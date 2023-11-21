<h1 align="center">Data Interfaces</h1>

## DTO Composition

A common use case for data interfaces is the composition of DTOs for a REST service.
Image a simple product for an online store. It has a name and a price (in cents).
That's all the data you need to create one.

```kotlin
data class ProductData(
    val name: String,
    val price: Long
)
```

To access the individual products from a list of them, we need to add an ID.

```kotlin
data class Product(
    val name: String,
    val price: Long,
    val id: Long
)
```

But how do we express this relationship in Kotlin?
Sure, we want to use data classes because that's what DTOs represent.
Unfortunately, Kotlin has no built-in mechanism for composing or embedding data classes.
The only thing you could do as a (not very pretty) workaround would be to add a
`ProductData` property to the `Product` class or live with a lot of copy-paste code.

On the other hand, there are Kotlin interfaces that allow much more composable definitions.

```kotlin
interface Product : ProductData {
    val id: Long
}

interface ProductData {
    val name: String
    val price: Long
}
```

Now one of the drawbacks is now the instantiation. You could create an anonymous object
each time a DTO is needed and override the properties on the fly. But this is too much overhead
and misses the core benefits of data classes such as generated `equals` and `toString` methods.

```kotlin
val book = object : Product {
    override val id = 1
    override val name = "My Book"
    override val price = 9_99L
}
```

Data Interfaces bridge the gap between the flexibility at definition time
and the ease of use of data classes.

```kotlin
@data private interface Product : ProductData {
    val id: Long
}

@data private interface ProductData {
    val name: String
    val price: Long
}
```

Make sure you make all your data interfaces private, so that you don't mix them with the generated data classes.

## How it works

First of all you need to annotate an interface with `@data`.

```kotlin
package com.example.dtos

@data private interface Example {
    val number: Int
}
```

Now we need to create data classes from the annotated interfaces.

```kotlin
package com.example.dtos.generated

data class Example(
    val number: Int
)
```

## Roadmap

### 1.0.1

Introduce a second generation mode (companion).
In this mode, the processor generates extension functions (operator invoke)
on the companion object of the interface to mimic a data class constructor.

The companion object must be added manually since KSP cannot modify existing sourcecode.

### 1.0.2

Use a Compiler Plugin (Companionate) to generate companion objects based on a meta-annotation.
