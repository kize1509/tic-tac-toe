plugins {
    kotlin("jvm")
    application
}
dependencies {
    implementation(project(":lib"))
}

application {
    mainClass = "org.jetbrains.kotlinx.tictactoe.MainKt"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(24))
    }
}