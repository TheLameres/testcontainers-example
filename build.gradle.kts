import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
    application
}

group = "thelameres"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(group = "org.postgresql", name = "postgresql", version = "42.2.22")
    implementation(group = "ch.qos.logback", name = "logback-classic", version = "1.2.3")
    testImplementation(kotlin("test-junit5"))
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-engine")
    val testContainersVersion = "1.15.3"
    testImplementation(group = "org.testcontainers", name = "testcontainers", version = testContainersVersion)
    testImplementation(group = "org.testcontainers", name = "postgresql", version = testContainersVersion)
    testImplementation(group = "org.testcontainers", name = "junit-jupiter", version = testContainersVersion)
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}