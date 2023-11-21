plugins {
	kotlin("multiplatform")
	id("com.google.devtools.ksp")

}

kotlin {
	jvm()

	sourceSets.jvmMain.dependencies {
		implementation(project(":annotations"))
	}
}

dependencies {
	add("kspJvm", project(":processor"))
}
