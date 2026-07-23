plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    val log4j2Version = "2.26.1"
    val lombokVersion = "1.18.46"
    val slf4jVersion = "2.0.18"
    val mockitoVersion = "5.23.0"
    val jacksonVersion = "3.2.1"
    val jackson2Version = "2.22.1"
    val junitVersion = "6.1.1"

    compileOnly("org.projectlombok:lombok:${lombokVersion}")
    annotationProcessor("org.projectlombok:lombok:${lombokVersion}")

    // logging
    implementation("org.slf4j:slf4j-api:${slf4jVersion}")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:${log4j2Version}")
    implementation("org.apache.logging.log4j:log4j-api:${log4j2Version}")
    implementation("org.apache.logging.log4j:log4j-core:${log4j2Version}")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${jackson2Version}")
    implementation("com.fasterxml.jackson.core:jackson-databind:${jackson2Version}")

    // testing
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:${junitVersion}")
    testImplementation("org.junit.jupiter:junit-jupiter:${junitVersion}")
    testImplementation("org.mockito:mockito-core:${mockitoVersion}")
    testImplementation("org.mockito:mockito-junit-jupiter:${mockitoVersion}")
    testImplementation("tools.jackson.dataformat:jackson-dataformat-yaml:${jacksonVersion}")

    testCompileOnly("org.projectlombok:lombok:${lombokVersion}")
    testAnnotationProcessor("org.projectlombok:lombok:${lombokVersion}")
}

application {
    mainClass.set("eu.fogas.parking.App")
}

tasks {
    named<JavaExec>("run") {
        standardInput = System.`in`
    }
    named<Test>("test") {
        useJUnitPlatform()
    }
}
