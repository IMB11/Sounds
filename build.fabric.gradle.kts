@file:Suppress("UnstableApiUsage")

plugins {
    id("net.fabricmc.fabric-loom")
    id("dev.kikugie.postprocess.jsonlang")
//    id("me.modmuss50.mod-publish-plugin")
}

tasks.named<ProcessResources>("processResources") {
    fun prop(name: String) = project.property(name) as String

    val props = HashMap<String, String>().apply {
        this["mod_version"] = prop("mod.version")
        this["target_minecraft"] = prop("mod.target")
        this["mod_id"] = "sounds"
        this["mod_name"] = "Sounds"
        this["mod_description"] = "A highly configurable sound overhaul mod that adds new sound effects while improving vanilla sounds too."
        this["mod_license"] = "ARR"
        this["target_yacl"] = "*"
        this["target_mru"] = ">=1.0.30"
        this["target_fabricloader"] = "0.17.2"
    }

    filesMatching(listOf("fabric.mod.json", "META-INF/neoforge.mods.toml", "META-INF/mods.toml")) {
        expand(props)
    }
}

version = "${property("mod.version")}+${property("deps.minecraft")}-fabric"
base.archivesName = property("mod.id") as String

jsonlang {
    languageDirectories = listOf("assets/${property("mod.id")}/lang")
    prettyPrint = true
}


stonecutter {
    replacements.string {
        direction = eval(current.version, ">1.21.10")
        replace("ResourceLocation", "Identifier")
    }
    replacements.string {
        direction = eval(current.version, ">1.21.10")
        replace("net.minecraft.Util", "net.minecraft.util.Util")
    }
    replacements.string {
        direction = eval(current.version, ">1.21.10")
        replace("ResourceKey::location", "ResourceKey::identifier")
    }
}

repositories {
    maven {
        name = "Terraformers (Mod Menu)"
        url = uri("https://maven.terraformersmc.com/releases/")
        content {
            includeGroupAndSubgroups("com.terraformersmc")
            includeGroupAndSubgroups("dev.emi")
        }
    }
    maven {
        name = "gegy"
        url = uri("https://maven.gegy.dev")
        content {
            includeGroupAndSubgroups("dev.yumi")
            includeGroupAndSubgroups("dev.lambdaurora")
        }
    }
    maven {
        name = "Modrinth"
        url = uri("https://api.modrinth.com/maven")
        content {
            includeGroupAndSubgroups("maven.modrinth")
        }
    }
    maven {
        name = "Xander Maven"
        url = uri("https://maven.isxander.dev/releases")
        content {
            includeGroupAndSubgroups("dev.isxander")
            includeGroupAndSubgroups("org.quiltmc.parsers")
        }
    }
    maven {
        name = "Cassian's Maven"
        url = uri("https://maven.cassian.cc/")
        content {
            includeGroupAndSubgroups("cc.cassian")
            includeGroupAndSubgroups("folk.sisby")
        }
    }
    mavenCentral()
    mavenLocal()
}

dependencies {
    minecraft("com.mojang:minecraft:${property("deps.minecraft")}")

    implementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")
    implementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")

    compileOnly("maven.modrinth:trashslot:${property("compile.trashslot")}")
    implementation("cc.cassian.mru:mru-fabric:${property("deps.mru")}")

    implementation("dev.isxander:yet-another-config-lib:${property("deps.yacl")}-fabric")
    implementation("com.terraformersmc:modmenu:${property("runtime.modmenu")}")
}

fabricApi {
    configureDataGeneration() {
        outputDirectory = file("$rootDir/src/main/generated")
        client = true
    }
}

tasks.named("processResources") {
    dependsOn(":${stonecutter.current.project}:stonecutterGenerate")
}

tasks {
    processResources {
        exclude("**/neoforge.mods.toml", "**/mods.toml")
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        from(jar.map { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/${project.property("mod.version")}"))
        dependsOn("build")
    }
}

java {
    withSourcesJar()
    val javaCompat = if (stonecutter.eval(stonecutter.current.version, ">26")) {
        JavaVersion.VERSION_25
    } else {
        JavaVersion.VERSION_21
    }
    sourceCompatibility = javaCompat
    targetCompatibility = javaCompat
}

val additionalVersionsStr = findProperty("publish.additionalVersions") as String?
        val additionalVersions: List<String> = additionalVersionsStr
        ?.split(",")
        ?.map { it.trim() }
        ?.filter { it.isNotEmpty() }
        ?: emptyList()
