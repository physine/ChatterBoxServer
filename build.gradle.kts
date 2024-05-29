plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.23"
}

group = "physine"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val ktorVersion = "2.3.10"
val koinVersion = "3.5.6"
val log4j2Version = "2.23.1"
val exposedVersion = "0.50.1"
val postgresqlVersion = "42.7.3"
val typesafeConfigVersion = "1.4.3"
val slf4jVersion = "2.0.13"
val logbackVersion = "1.5.6"

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

    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("ch.qos.logback:logback-core:$logbackVersion")
    implementation("org.slf4j:slf4j-api:$slf4jVersion")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")

    implementation("org.postgresql:postgresql:$postgresqlVersion")

    implementation("com.typesafe:config:$typesafeConfigVersion")
}

tasks.test {
    useJUnitPlatform()
}