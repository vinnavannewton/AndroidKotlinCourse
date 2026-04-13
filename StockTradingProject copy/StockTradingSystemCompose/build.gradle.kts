plugins {
    kotlin("jvm") version "2.1.0"
    id("org.jetbrains.compose") version "1.7.3"
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.0"
}

kotlin {
    jvmToolchain(21)
}

group = "com.stock"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "com.stock.ui.MainKt"
        jvmArgs += listOf(
            "-Xms64m",          // Start with 64MB heap
            "-Xmx256m",         // Cap at 256MB heap (was unbounded)
            "-XX:+UseG1GC",     // G1 GC — good balance of throughput and low pause
            "-XX:MaxGCPauseMillis=50", // Keep GC pauses under 50ms for UI responsiveness
            "-XX:+UseStringDeduplication", // Deduplicate identical strings in heap
        )
    }
}
