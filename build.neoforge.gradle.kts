plugins {
    id("net.neoforged.moddev")
    id ("dev.kikugie.postprocess.jsonlang")
    id("me.modmuss50.mod-publish-plugin")
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
        this["target_mru"] = "1.0.30"
        this["target_loader"] = "[4, )"
        this["loader"] = "neoforge"
    }

    filesMatching(listOf("fabric.mod.json", "META-INF/neoforge.mods.toml", "META-INF/mods.toml")) {
        expand(props)
    }
}

version = "${property("mod.version")}+${property("deps.minecraft")}-neoforge"
base.archivesName = property("mod.id") as String

jsonlang {
    languageDirectories = listOf("assets/${property("mod.id")}/lang")
    prettyPrint = true
}

repositories {
    maven {
        name = "Cassian's Maven"
        url = uri("https://maven.cassian.cc")
        content {
            includeGroupAndSubgroups("cc.cassian")
            includeGroupAndSubgroups("folk.sisby")
        }
    }
    maven {
        name = "Terraformers (Mod Menu)"
        url = uri("https://maven.terraformersmc.com/releases/")
        content {
            includeGroupAndSubgroups("com.terraformersmc")
            includeGroupAndSubgroups("dev.emi")
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
        name = "Modrinth"
        url = uri("https://api.modrinth.com/maven")
        content {
            includeGroupAndSubgroups("maven.modrinth")
        }
    }
    maven {
        name = "Kotlin for Forge"
        url = uri("https://thedarkcolour.github.io/KotlinForForge/")
        content {
            includeGroupAndSubgroups("thedarkcolour")
        }
    }
    mavenCentral()
    mavenLocal()
}

neoForge {
    enable {
        version = property("deps.neoforge") as String
        // Disable recompilation if the "CI" environment variable is set to true. It is automatically set by GitHub Actions.
        isDisableRecompilation = System.getenv("CI") == "true"
    }
    validateAccessTransformers = true

    runs {
        register("client") {
            gameDirectory = file("run/")
            client()
        }
        register("server") {
            gameDirectory = file("run/")
            server()
        }
    }

    mods {
        register(property("mod.id") as String) {
            sourceSet(sourceSets["main"])
        }
    }
    sourceSets["main"].resources.srcDir("src/main/generated")
}

dependencies {
    implementation("cc.cassian.mru:mru-neoforge:${property("deps.mru")}") {
        isTransitive = false
    }

    compileOnly("maven.modrinth:trashslot:${property("compile.trashslot")}")

    implementation("dev.isxander:yet-another-config-lib:${property("deps.yacl")}-neoforge")
}

tasks.named("processResources") {
    dependsOn(":${stonecutter.current.project}:stonecutterGenerate")
}

tasks {
    processResources {
        exclude("**/fabric.mod.json", "**/*.accesswidener", "**/mods.toml")
    }

    named("createMinecraftArtifacts") {
        dependsOn("stonecutterGenerate")
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
