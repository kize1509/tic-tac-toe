plugins {
    kotlin("jvm")
}

dependencies {
    implementation(libs.bundles.kotlinxEcosystem)
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(24)
}