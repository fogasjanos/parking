plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    val log4j2Version = "2.24.3"
    val lombokVersion = "1.18.36"
    val slf4jVersion = "2.0.16"
    val mockitoVersion = "5.14.2"
    val jacksonVersion = "2.18.2"
    val junitVersion = "5.11.4"

    compileOnly("org.projectlombok:lombok:${lombokVersion}")
    annotationProcessor("org.projectlombok:lombok:${lombokVersion}")

    // logging
    implementation("org.slf4j:slf4j-api:${slf4jVersion}")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:${log4j2Version}")
    implementation("org.apache.logging.log4j:log4j-api:${log4j2Version}")
    implementation("org.apache.logging.log4j:log4j-core:${log4j2Version}")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${jacksonVersion}")

    // testing
    testImplementation("org.junit.jupiter:junit-jupiter:${junitVersion}")
    testImplementation("org.mockito:mockito-core:${mockitoVersion}")
    testImplementation("org.mockito:mockito-junit-jupiter:${mockitoVersion}")

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
