val javaVersion = "16"
val projectName = "apdlll.scores"
group = projectName
version = "1.0"
java.sourceCompatibility = JavaVersion.toVersion(javaVersion)

plugins {
    application
    jacoco
    kotlin("jvm") version "1.7.22"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
    id("org.owasp.dependencycheck") version "8.2.0"
    id("org.jetbrains.dokka") version "1.7.20"
    id("com.github.ben-manes.versions") version "0.44.0"
    id("com.adarshr.test-logger") version "3.2.0"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

application {
    mainClass.set("$projectName.MainKt")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = javaVersion
}

// Tests config

tasks.test {
    useJUnitPlatform()
    reports.junitXml.outputLocation.set(reports.html.outputLocation.get())
    filter {
        excludeTestsMatching("$projectName.test.integration.*")
    }
}

tasks.register("inttest", Test::class) {
    group = "aliases"
    useJUnitPlatform()
    reports.junitXml.outputLocation.set(reports.html.outputLocation.get())

    filter {
        includeTestsMatching("$projectName.test.integration.*")
    }
}

// Code style checks

ktlint {
    verbose.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.JSON)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.HTML)
    }
    filter {
        exclude("**/style-violations.kt")
    }
}

tasks.withType<org.jlleitschuh.gradle.ktlint.tasks.GenerateReportsTask>() {
    reportsOutputDirectory.set(
        project.layout.buildDirectory.dir("reports/style")
    )
}

tasks.register("checkstyle") {
    group = "aliases"
    dependsOn(tasks.ktlintCheck)
    doLast { println("Report generated at: ./build/reports/style/") }
}

tasks.register("fixstyle") {
    group = "aliases"
    dependsOn(tasks.ktlintFormat)
}

// Static code analysis

detekt {
    buildUponDefaultConfig = true
    // config = files("$rootDir/config/detekt/detekt.yml")
    source = files("$projectDir/src/main")
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    jvmTarget = javaVersion
    reports {
        html.required.set(true)
        html.outputLocation.set(buildDir.resolve("reports/code/detekt.html"))
        xml.required.set(true)
        xml.outputLocation.set(buildDir.resolve("reports/code/detekt.xml"))
        txt.required.set(true)
        txt.outputLocation.set(buildDir.resolve("reports/code/detekt.txt"))
        sarif.required.set(true)
        sarif.outputLocation.set(buildDir.resolve("reports/code/detekt.sarif"))
        md.required.set(true)
        md.outputLocation.set(buildDir.resolve("reports/code/detekt.md"))
    }
}

tasks.register("checkcode") {
    group = "aliases"
    dependsOn(tasks.detekt)
    doLast { println("Report generated at: ./build/reports/code/") }
}

// Dependencies vulnerabilities checks

dependencyCheck {
    format = org.owasp.dependencycheck.reporting.ReportGenerator.Format.ALL
    outputDirectory = buildDir.resolve("reports/vulnerabilities").absolutePath
    analyzers.assemblyEnabled = false
}

tasks.register("vulnerabilities") {
    group = "aliases"
    dependsOn(tasks.dependencyCheckAnalyze)
    doLast { println("Report generated at: ./build/reports/vulnerabilities/") }
}

// Dependencies updates

tasks.named<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask>("dependencyUpdates").configure {

    fun String.isNonStable(): Boolean {
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { toUpperCase().contains(it) }
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        val isStable = stableKeyword || regex.matches(this)
        return isStable.not()
    }

    rejectVersionIf {
        candidate.version.isNonStable()
    }

    outputDir = "build/reports/deps"
    checkForGradleUpdate = true
    outputFormatter = "json,xml,html"
}

tasks.register("deps") {
    group = "aliases"
    dependsOn(tasks.dependencyUpdates)
    doLast { println("Report generated at: ./build/reports/deps/") }
}

// Coverage reports

jacoco {
    reportsDirectory.set(layout.buildDirectory.dir("reports/coverage"))
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.9".toBigDecimal()
            }
        }
    }
}

tasks.register("coverage") {
    group = "aliases"
    dependsOn(tasks.jacocoTestReport)
    doLast { println("Report generated at: ./build/reports/coverage/") }
}

// Doc for the main contract interface

tasks.dokkaHtml.configure {
    outputDirectory.set(buildDir.resolve("sources-doc"))
    dokkaSourceSets {
        configureEach {
            perPackageOption {
                matchingRegex.set("""^(?!$projectName.model$).*$""")
                suppress.set(true)
            }
        }
    }
}

tasks.register("doc") {
    group = "aliases"
    dependsOn(tasks.dokkaHtml)
    doLast { println("Docs generated at: ./build/sources-doc/") }
}

// Add sample files to the distribution

distributions {
    main {
        contents {
            val samples = "src/test/resources/tournament1"
            into("bin/$samples") {
                from(samples)
            }
        }
    }
}

// Include tasks in the build pipeline

tasks.check {
    dependsOn(tasks.jacocoTestReport)
    dependsOn(tasks.jacocoTestCoverageVerification)
    dependsOn(tasks.dependencyUpdates)
    dependsOn(tasks.dependencyCheckAnalyze)
    dependsOn(tasks.get("inttest"))
}

tasks.build {
    dependsOn(tasks.dokkaHtml)
}
