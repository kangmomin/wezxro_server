import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"

	kotlin("jvm") version "1.9.20"
	kotlin("kapt") version "1.9.20"
	kotlin("plugin.spring") version "1.9.20"
	kotlin("plugin.jpa") version "1.9.20"
	kotlin("plugin.serialization") version "1.5.0"

	idea
}

group = "com.hwalaon"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("io.lettuce:lettuce-core")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.google.code.gson:gson")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation ("io.jsonwebtoken:jjwt-api:0.11.2")
	implementation ("io.jsonwebtoken:jjwt-jackson:0.11.2")
	implementation("org.postgresql:postgresql")
	implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
	implementation("com.squareup.okhttp3:okhttp") // OkHttp

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")

	kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
	kapt("jakarta.annotation:jakarta.annotation-api")
	kapt("jakarta.persistence:jakarta.persistence-api")

	// JWT
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

idea {
	module {
		val kaptMain = file("build/generated/source/kapt/main")
		sourceDirs.add(kaptMain)
		generatedSourceDirs.add(kaptMain)
	}
}