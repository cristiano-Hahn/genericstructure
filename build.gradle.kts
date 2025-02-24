plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.1"
	id("org.flywaydb.flyway") version "9.0.4"
	id("org.jmailen.kotlinter") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "com.projects"
version = "0.0.1"

val kotestConsoleJvmVersion = "4.1.3.2"
val kotestVersion = "5.5.5"
val kotestSpringVersion = "4.4.3"
val kotestExtensionsSpringVersion = "1.1.2"
val mockkVersion = "1.13.11"
val springMockkVersion = "3.1.1"
val jwtVersion = "0.12.6"
val loggingVersion = "3.0.5"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

buildscript {
	repositories {
		mavenCentral()
	}
	dependencies {
		classpath("org.postgresql:postgresql:42.2.18") // required by flyway
	}
}


repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework:spring-jdbc")
	implementation("org.postgresql:postgresql")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation ("io.jsonwebtoken:jjwt-api:$jwtVersion")
	implementation ("io.jsonwebtoken:jjwt-impl:$jwtVersion")
	implementation ("io.jsonwebtoken:jjwt-orgjson:$jwtVersion")
	implementation("io.github.microutils:kotlin-logging:$loggingVersion")

	testImplementation("com.ninja-squad:springmockk:$springMockkVersion")
	testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
	testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
	testImplementation("io.kotest.extensions:kotest-extensions-spring:$kotestExtensionsSpringVersion")
	testImplementation("io.kotest:kotest-runner-console-jvm:$kotestConsoleJvmVersion") {
		exclude("com.github.ajalt")
	}
	testImplementation("io.mockk:mockk:$mockkVersion")
	testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug")
	testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	annotationProcessor("org.projectlombok:lombok")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks{
	flyway {
		url = "jdbc:postgresql://localhost:5432/generic_structure_db"
		user = "generic_structure_user"
		password = "generic_structure_password"
		schemas = arrayOf("public")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
