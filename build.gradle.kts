plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    val log4j2Version = "2.17.2"
    val lombokVersion = "1.18.24"
    val slf4jVersion = "1.7.36"
    val mockitoVersion = "4.6.1"
    val jacksonVersion = "2.13.3"
    val junitVersion = "5.8.2"

    compileOnly("org.projectlombok:lombok:${lombokVersion}")
    annotationProcessor("org.projectlombok:lombok:${lombokVersion}")

    // logging
    implementation("org.slf4j:slf4j-api:${slf4jVersion}")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:${log4j2Version}")
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
