plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "2.0.0"
}

dependencies {
    implementation(libs.bundles.kotlinxEcosystem)
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
}

kotlin {
    jvmToolchain(24)
}