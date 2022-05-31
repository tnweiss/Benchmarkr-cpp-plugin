//plugins {
//    id("java-library")
//}
//
//java {
//    sourceCompatibility = JavaVersion.VERSION_11
//    targetCompatibility = JavaVersion.VERSION_11
//}

val intellijVersion by rootProject.extra { "1.5.2" }

allprojects {
    group = "com.github.benchmarkr"
    version  = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

//ext {
//    intellijVersion = "2021.2"
//    intellijPluginVersion = "1.5.2"
//}

