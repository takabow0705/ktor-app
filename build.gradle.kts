plugins {
    `kotlin-dsl`
}

allprojects {
    repositories {
        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
    }
}

tasks.withType<Wrapper> {
    gradleVersion = "8.1.1"
}

tasks.withType(Delete::class).getByName("clean") {
    delete("$projectDir/buildSrc/build")
}
