pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    
}
rootProject.name = "pianist-charts"

include(":pianist")

include("example-apps:android")
include("example-apps:desktop")
include("example-apps:common")

