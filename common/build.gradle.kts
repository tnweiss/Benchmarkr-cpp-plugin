plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.5.2"
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

intellij {
    version.set("2021.2")
}

tasks.test {
    useJUnitPlatform()
}