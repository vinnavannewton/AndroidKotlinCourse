import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform") version "2.1.0"
    id("com.android.application") version "8.5.2"
    id("org.jetbrains.compose") version "1.7.3"
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.0"
}

group = "com.stock"
version = "1.0.0"

kotlin {
    androidTarget()
    jvm("desktop")
    jvmToolchain(17)

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.activity:activity-compose:1.9.3")
            }
        }
    }
}

android {
    namespace = "com.stock.stockflow"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.stock.stockflow"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }

    // ✅ FIX: Tell AGP where sources/manifest are in KMP flat layout
    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            kotlin.srcDirs("src/androidMain/kotlin")
            res.srcDirs("src/androidMain/res")
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.stock.desktop.MainKt"

        // ✅ FIX: Use JAVA_HOME so jlink gets the full JDK
        javaHome = System.getenv("JAVA_HOME") ?: ""

        // ✅ FIX: Explicitly list modules so jlink doesn't need to scan everything
        nativeDistributions {
            targetFormats(
                TargetFormat.Deb,
                TargetFormat.Rpm,
                TargetFormat.Exe,    // Windows only
                TargetFormat.Msi     // Windows only
            )
            packageName = "StockFlow"
            packageVersion = "1.0.0"
            description = "StockFlow Stock Trading Simulator"

            modules(
                "java.base",
                "java.desktop",
                "java.logging",
                "java.net.http",
                "java.prefs",
                "java.sql",
                "java.xml",
                "java.naming",
                "java.security.jgss",
                "jdk.unsupported"
            )

            linux {
                packageName = "stockflow"
                debMaintainer = "stockflow@example.com"
                menuGroup = "Finance"
                appCategory = "Finance"
            }

            windows {
                menuGroup = "StockFlow"
                upgradeUuid = "B4A2F3C1-5D6E-4F7A-8B9C-0D1E2F3A4B5C"
                perUserInstall = true
            }
        }
    }
}
