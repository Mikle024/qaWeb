plugins {
    id("java")
}

group = "ru.netology.qa"
version = "1.0-SNAPSHOT"

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.1")
    testImplementation("org.seleniumhq.selenium:selenium-java:4.11.0")
    testImplementation("io.github.bonigarcia:webdrivermanager:5.4.1")
}

tasks.test {
    useJUnitPlatform()
}