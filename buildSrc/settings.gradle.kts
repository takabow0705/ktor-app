dependencyResolutionManagement {
    versionCatalogs{
        create("pluginLibs"){
            val kotlinVersion = version("kotlin", "1.8.10")
            library("gradle-kotlin", "org.jetbrains.kotlin", "kotlin-gradle-plugin").versionRef(kotlinVersion)
            library(
                "gradle-kotlinx-serialization",
                "org.jetbrains.kotlin.plugin.serialization",
                "org.jetbrains.kotlin.plugin.serialization.gradle.plugin"
            ).versionRef(kotlinVersion)

            val nodePluginVersion = version("node", "5.0.0")
            library("gradle-node", "com.github.node-gradle", "gradle-node-plugin").versionRef(nodePluginVersion)

            val protoVersion = version("proto", "0.9.+")
            library("gradle-protobuf", "com.google.protobuf", "protobuf-gradle-plugin").versionRef(protoVersion)

            val spotlessVersion = version("spotless", "6.19.0")
            library("gradle-spotless","com.diffplug.spotless", "com.diffplug.spotless.gradle.plugin").versionRef(spotlessVersion)
        }
    }
}
