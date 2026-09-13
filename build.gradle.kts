plugins {
    java
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.logging.log4j:log4j-api:2.25.3")
    implementation("org.apache.logging.log4j:log4j-core:2.25.3")

    testImplementation(platform("org.junit:junit-bom:6.1.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

val logLevel = (project.findProperty("logLevel") as String?) ?: "warn"

tasks.register<JavaExec>("run") {
    group = "application"
    description = "Solves all 33 levels and writes <outputDir>/solution.txt (default: generated)."
    classpath = sourceSets.test.get().runtimeClasspath
    mainClass.set("blox.Runner")
    args((project.findProperty("outputDir") as String?)?.let { listOf(it) } ?: listOf("generated"))
    jvmArgs("-Dblox.log.level=$logLevel")
}

tasks.register<JavaExec>("validate") {
    group = "application"
    description = "Validates a moves file against the real rules (default: generated/solution.txt)."
    classpath = sourceSets.test.get().runtimeClasspath
    mainClass.set("blox.Validator")
    args((project.findProperty("moves") as String?) ?: "generated/solution.txt")
    jvmArgs("-Dblox.log.level=$logLevel")
}
