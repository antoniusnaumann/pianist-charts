import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

val pianistVersion: String by project

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "1.1.1"
}

group = "dev.antonius"
version = pianistVersion

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":example-apps:common"))
                implementation(compose.desktop.currentOs)
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            this.
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "jvm"
            packageVersion = pianistVersion.versionCore
            macOS.dmgPackageVersion = packageVersion.takeIf { it != null && it.first() != '0' } ?: "1.0.0"
        }
    }
}

val String.versionCore: String get() = split('-', '+').first()