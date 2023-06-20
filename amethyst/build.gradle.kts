import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("fabric-loom") version "1.2-SNAPSHOT"
	id("org.jetbrains.kotlin.jvm") version "1.8.22"
}

version = project.properties["mod_version"]!!
group = project.properties["maven_group"]!!

base {
	archivesName.set("${project.properties["archives_base_name"]}")
}

repositories {

}

loom {
    splitEnvironmentSourceSets()

	mods {
		maybeCreate("amethyst").apply {
			sourceSet(sourceSets["main"])
			sourceSet(sourceSets["client"])
		}
	}
}

dependencies {
	// Minecraft
	minecraft("com.mojang:minecraft:${project.properties["minecraft_version"]}")
	mappings("net.fabricmc:yarn:${project.properties["yarn_mappings"]}:v2")
	modImplementation("net.fabricmc:fabric-loader:${project.properties["loader_version"]}")

	// Fabric API
	modImplementation("net.fabricmc.fabric-api:fabric-api:${project.properties["fabric_version"]}")
	modImplementation("net.fabricmc:fabric-language-kotlin:${project.properties["fabric_kotlin_version"]}")
}

tasks {
	processResources {
		inputs.property("version", project.version)

		filesMatching("fabric.mod.json") {
			expand("version" to project.version)
		}
	}

	jar {
		from("LICENSE") {
			rename { "${it}_${project.archivesName}" }
		}
	}

	withType(JavaCompile::class).configureEach {
		options.release.set(17)
	}

	withType(KotlinCompile::class).all {
		kotlinOptions {
			jvmTarget = "17"
		}
	}
}

java {
	withSourcesJar()

	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}