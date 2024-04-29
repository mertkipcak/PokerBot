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

    implementation("no.tornado:tornadofx:1.7.20")
    implementation("org.openjfx:javafx-controls:16")
    implementation("org.openjfx:javafx-graphics:16")
}

application {
    mainClass.set("org.mkipcak.app.MainKt")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(18)
}
