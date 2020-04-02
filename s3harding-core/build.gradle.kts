plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    `maven-publish`
}

dependencies {

    implementation(kotlin("stdlib-jdk8"))
    implementation("commons-codec:commons-codec:1.14")

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.0")

}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.dokka {
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val dokkaJar by tasks.registering(Jar::class) {
    description = "Assembles Kotlin docs with Dokka"
    archiveClassifier.set("javadoc")
    from(tasks.dokka)
}

publishing {

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/hexindai/s3harding")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }

    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
            artifact(sourcesJar.get())
            artifact(dokkaJar.get())

            pom {
                name.set("s3harding core")
                description.set("S3harding: A sharding library for Mybatis")
                url.set("https://github.com/hexindai/s3harding")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("Runrioter")
                        name.set("Runrioter Wung")
                        email.set("runrioter@gmail.com")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/hexindai/s3harding.git")
                    developerConnection.set("scm:git:ssh://github.com:hexindai/s3harding.git")
                    url.set("https://github.com/hexindai/s3harding/tree/master/s3harding-core")
                }
            }
        }
    }
}