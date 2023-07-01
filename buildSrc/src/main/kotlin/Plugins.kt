import org.jetbrains.kotlinx.serialization.gradle.SerializationGradleSubplugin
import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin
import com.github.gradle.node.NodePlugin
import com.github.gradle.node.npm.task.NpmTask
import com.google.protobuf.gradle.GenerateProtoTask
import com.google.protobuf.gradle.ProtobufExtension
import com.google.protobuf.gradle.ProtobufPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ApplicationPlugin
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.tasks.Delete
import org.gradle.plugins.ide.idea.IdeaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.Copy
import org.gradle.jvm.tasks.Jar
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.api.tasks.testing.Test
import org.gradle.external.javadoc.CoreJavadocOptions
import com.google.protobuf.gradle.id
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JvmVendorSpec
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class CommonCustomPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            apply<BasePlugin>()
            apply<IdeaPlugin>()

            tasks.withType(Delete::class).getByName("clean") {
                delete("$projectDir/out")
            }
        }
    }
}

class JavaCustomPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            apply<CommonCustomPlugin>()
            apply<JavaBasePlugin>()
            apply<JavaLibraryPlugin>()
            apply<ApplicationPlugin>()
            apply<SpotlessPlugin>()

            extensions.getByType<JavaPluginExtension>().apply {
                withJavadocJar()
                withSourcesJar()
                toolchain {
                    languageVersion.set(JavaLanguageVersion.of(17))
                    vendor.set(JvmVendorSpec.AMAZON)
                }
            }

            extensions.getByType<SpotlessExtension>().apply(){
                java {
                    importOrder()
                    removeUnusedImports()
                    googleJavaFormat()
                    cleanthat()
                }
            }

            tasks.withType<JavaCompile>().configureEach {
                options.encoding = "UTF-8"
            }

            tasks.withType<Javadoc>().configureEach {
                options.encoding = "UTF-8"
                (options as CoreJavadocOptions).addStringOption("Xdoclint:none", "-quiet")
            }

            tasks.withType<Jar> {
                exclude(".gitkeep")
            }

            tasks.withType<Test> {
                useJUnitPlatform()
                systemProperties = mapOf(
                    "junit.jupiter.execution.parallel.enabled" to "true",
                    "junit.jupiter.execution.parallel.config.strategy" to "dynamic"
                )
                testLogging.showStandardStreams = true
            }
        }
    }
}

class KotlinCustomPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run{
            apply<JavaCustomPlugin>()
            apply<KotlinPluginWrapper>()
            apply<SerializationGradleSubplugin>()

            extensions.getByType<SpotlessExtension>().apply(){
                kotlin {
                    ktfmt().googleStyle()
                }
                kotlinGradle {
                    target("*.kts")
                }
            }

            tasks.withType<KotlinCompile>().configureEach {
                kotlinOptions {
                    apiVersion = "1.8"
                    languageVersion = "1.8"
                    jvmTarget = "17"
                }
            }

            tasks.getByName<Copy>("processResources") {
                exclude("**/.gitkeep")
            }
        }
    }
}

class GrpcCustomPlugin : Plugin<Project>{
    private val protocArtifact = "com.google.protobuf:protoc:3.22.+"
    private val grpcJavaGenArtifact = "io.grpc:protoc-gen-grpc-java:1.54.+"
    private val grpcKotlinGenArtifact = "io.grpc:protoc-gen-grpc-kotlin:1.3.+:jdk8@jar"

    override fun apply(target: Project) {
        target.run {
            apply<KotlinCustomPlugin>()
            apply<ProtobufPlugin>()
            extensions.getByType<ProtobufExtension>().apply {
                protoc { artifact = protocArtifact }

                plugins {
                    id("grpc") { artifact = grpcJavaGenArtifact }
                    id("grpckt") { artifact = grpcKotlinGenArtifact }
                }

                generateProtoTasks {
                    all().forEach {
                        it.plugins {
                            id("grpc") { outputSubDir = "java" }
                            id("grpckt") { outputSubDir = "kotlin" }
                        }
                    }
                }
            }

            tasks.withType<GenerateProtoTask> { }

            tasks.withType<KotlinCompile> {
                kotlinOptions {
                    freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
                }
            }

            tasks.getByName("processResources").dependsOn("generateProto")
        }
    }
}

class NodeCustomPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            apply<CommonCustomPlugin>()
            apply<NodePlugin>()

            task<NpmTask>("build-web") {
                //ToDo
            }
            tasks.getByName("build").dependsOn("build-web")
        }
    }
}
