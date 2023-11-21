plugins {
    kotlin("multiplatform") version "1.9.20" apply false
	id("com.google.devtools.ksp") version "1.9.20-1.0.14" apply false
}

allprojects {
    group = "io.github.merlinths"
    version = "1.0-SNAPSHOT"
}
