plugins {
    java
    kotlin("jvm") version "1.4.0"
    kotlin("plugin.spring") version "1.4.0"
    kotlin("plugin.allopen") version "1.4.0"
    kotlin("plugin.jpa") version "1.4.0"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    id("org.springframework.boot") version "2.4.0"
}

group = "com.github.tkaczenko.lifepanel"
version = "1.0-SNAPSHOT"

val javaVersion = JavaVersion.VERSION_11
java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

repositories {
    mavenCentral()
    maven(url = "https://repo.spring.io/snapshot")
    maven(url = "https://repo.spring.io/milestone")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("net.bull.javamelody:javamelody-spring-boot-starter:1.86.0")
    implementation("commons-codec:commons-codec:1.15")
    implementation("io.springfox:springfox-swagger2:3.0.0")
    implementation("io.springfox:springfox-spi:3.0.0")
    implementation("io.springfox:springfox-spring-web:3.0.0")
    implementation("io.springfox:springfox-swagger-ui:3.0.0")
    implementation("io.springfox:springfox-swagger-common:3.0.0")
    implementation("io.springfox:springfox-core:3.0.0")
    implementation("com.google.api-client:google-api-client:1.30.4")
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.30.6")
    implementation("com.google.apis:google-api-services-sheets:v4-rev581-1.25.0")
    testImplementation("junit", "junit", "4.12")
}