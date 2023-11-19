plugins { kotlin("multiplatform") }

kotlin {
    jvm()

	sourceSets {
		jvmMain.dependencies {
			implementation(project(":annotations"))
			implementation(libs.ksp)
			implementation(libs.bundles.kotlinpoet)
		}

		jvmTest.dependencies {
			implementation(project(":annotations"))
			implementation(testLibs.compile.testing)
			implementation(testLibs.bundles.kotest)
		}
	}
}

tasks.withType<Test>().configureEach {
	useJUnitPlatform()
}
