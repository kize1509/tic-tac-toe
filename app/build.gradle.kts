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


tasks.run{
    standardInput = System.`in`
    dependsOn(":lib:build")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(24))
    }
}