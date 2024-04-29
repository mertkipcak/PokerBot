plugins {
    kotlin("jvm") version "1.9.23"
    application
}

group = "org.mkipcak"
version = "1.0-0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

application {
    mainClass.set("org.mkipcak.MainKt")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(18)
}
