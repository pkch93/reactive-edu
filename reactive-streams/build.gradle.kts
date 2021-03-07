val reactiveStreamsVersion: String by project
val junitVersion: String by project
val assertJVersion: String by project

plugins {
    id("java")
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.reactivestreams:reactive-streams:${reactiveStreamsVersion}")
    implementation("org.reactivestreams:reactive-streams-tck:${reactiveStreamsVersion}")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testImplementation("org.assertj:assertj-core:${assertJVersion}")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
