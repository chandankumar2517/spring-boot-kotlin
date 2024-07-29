import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
	val kotlinVersion = "1.9.24"
	id("org.springframework.boot") version "3.3.2"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("jvm") version kotlinVersion
	id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
}

group = "hello" // A company name, for example, `org.jetbrains`
version = "1.0.0-SNAPSHOT" // Version to assign to the built artifact

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.h2database:h2")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.named<BootBuildImage>("bootBuildImage") {
	// For multi arch (Apple Silicon) support
	builder.set("paketobuildpacks/builder-jammy-buildpackless-tiny")
	buildpacks.set(listOf("paketobuildpacks/java"))
}

tasks.withType<Test> {
	useJUnitPlatform()
}

