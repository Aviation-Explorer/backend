plugins {
    id("com.github.johnrengelman.shadow")
    id("io.micronaut.application")
    id("io.micronaut.test-resources") version "4.4.0"
    id("io.micronaut.aot") version "4.4.0"
    id("java")
    id("groovy")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    manifest {
        attributes["Main-Class"] = "aviation.userService.UserServiceApplication"
    }
}

dependencies {

    val micronautVersion = "4.4.0"
    val spockVersion = "2.3-groovy-4.0"

    annotationProcessor("io.micronaut:micronaut-http-validation")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut.mongodb:micronaut-mongo-reactive")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("org.yaml:snakeyaml")
    testImplementation("io.micronaut.test:micronaut-test-spock:$micronautVersion")
    testImplementation("org.spockframework:spock-core:$spockVersion")
    testImplementation("io.micronaut:micronaut-http-client")
}

micronaut {
    runtime("netty")
    testRuntime("spock")
    processing {
        incremental(true)
        annotations("aviation.userService.*")
    }
}

application {
    mainClass.set("aviation.userService.UserServiceApplication")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
