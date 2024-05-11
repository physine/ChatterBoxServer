plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.23"
//    id("io.ktor.plugin") version "2.3.10"
}
group = "physine"

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val ktorVersion = "2.3.10"
val koinVersion = "3.5.6"
val log4j2Version = "2.23.1"

dependencies {
    testImplementation(kotlin("test"))

    implementation ("org.apache.logging.log4j:log4j-slf4j-impl:$log4j2Version")
    implementation ("org.apache.logging.log4j:log4j-api:$log4j2Version")
    implementation ("org.apache.logging.log4j:log4j-core:$log4j2Version")

    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")

    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-ktor:$koinVersion")
}

tasks.test {
    useJUnitPlatform()
}