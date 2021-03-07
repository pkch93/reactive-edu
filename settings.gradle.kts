rootProject.name = "reactive-edu"

pluginManagement {
    val kotlinJvmPluginVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinJvmPluginVersion
    }
}

include(":reactive-streams")
include(":reactor")
