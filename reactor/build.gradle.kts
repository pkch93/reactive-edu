val javaVersion: String by project
val reactorVersion: String by project
val reactorKotlinExtensionVersion: String by project
val junitVersion: String by project
val assertJVersion: String by project

plugins {
    id("java")
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.toVersion(javaVersion)
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("io.projectreactor:reactor-core:${reactorVersion}")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:${reactorKotlinExtensionVersion}")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testImplementation("org.assertj:assertj-core:${assertJVersion}")
    testImplementation("io.projectreactor:reactor-test:${reactorVersion}")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
