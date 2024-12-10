import groovy.cli.Option
import org.jetbrains.kotlin.gradle.plugin.extraProperties

// findme2
plugins {
    kotlin("jvm") version "2.0.21"
    java
    application
}

group = "zip.sadan"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

application {
    if (System.getProperty("DEBUG") != null) mainClass.set("zip.sadan.DebugKt") else mainClass.set("zip.sadan.MainKt")
}

tasks.withType<JavaExec> {
    if(name == "run") {
        systemProperty("YEAR", System.getProperty("YEAR"))
        systemProperty("DAY", System.getProperty("DAY"))
    }
}