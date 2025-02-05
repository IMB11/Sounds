plugins {
    id("dev.isxander.modstitch.base") version "0.5.12"
}

fun prop(name: String, consumer: (prop: String) -> Unit) {
    (findProperty(name) as? String?)?.let(consumer)
}

val loader = property("loom.platform") as String
val mcVersion = property("deps.minecraft") as String

repositories {
    mavenCentral()
    maven("https://maven.neoforged.net/releases/")
    mavenLocal()
    maven("https://mvn.devos.one/snapshots/")
    maven("https://maven.wispforest.io")
    maven("https://maven.imb11.dev/releases")
    maven("https://maven.neoforged.net/releases/")
    maven("https://maven.isxander.dev/releases")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://maven.quiltmc.org/repository/release")
    maven("https://maven.shedaniel.me/")
    maven("https://maven.terraformersmc.com/releases")
    exclusiveContent {
        forRepository {
            maven("https://api.modrinth.com/maven")
        }
        filter {
            includeGroup("maven.modrinth")
        }
    }
}

modstitch {
    minecraftVersion = mcVersion
    javaTarget = if (stonecutter.eval(mcVersion, ">1.20.4")) 21 else 17

    metadata {
        modId = "sounds"
        modName = "Sounds"
        modVersion = "${property("mod.version") as String}+${mcVersion}+${loader}"
        modGroup = "dev.imb11"
    }

    loom {
        fabricLoaderVersion = "+"

        configureLoom {
            accessWidenerPath = file("../../src/main/resources/sounds.accesswidener")

            mixin {
                useLegacyMixinAp = true
            }

            runs {
                all {
                    runDir = "../../run"
                }

                if (loader == "fabric" && stonecutter.eval(mcVersion, ">=1.21")) {
                    create("datagenClient") {
                        client()
                        name = "Data Generation Client"
                        vmArg("-Dfabric-api.datagen")
                        vmArg("-Dfabric-api.datagen.output-dir=" + project.rootDir.toPath().resolve("src/main/generated"))
                        vmArg("-Dfabric-api.datagen.modid=sounds")
                        runDir = "build/datagen"
                    }
                }
            }
        }
    }

    moddevgradle {
        defaultRuns()
        configureNeoforge {
            // empty configuration
        }
    }

    mixin {
        configs.register("sounds")

        if (loader == "fabric") configs.register("sounds-fabric")
        if (loader == "neoforge") configs.register("sounds-neoforge")
    }
}

stonecutter {
    consts(
        "fabric" to (property("loom.platform") as String == "fabric"),
        "neoforge" to (property("loom.platform") as String == "neoforge"),
        "forgelike" to (property("loom.platform") as String == "neoforge")
    )
}

dependencies {
    modstitch.loom {
            modstitchModImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")
            modImplementation("com.terraformersmc:modmenu:${property("runtime.modmenu")}")
            val mixinExtrasFabric = "io.github.llamalad7:mixinextras-fabric:0.5.0-beta.2"
            modstitchJiJ(mixinExtrasFabric)
            modstitchImplementation(mixinExtrasFabric)
    }

    modstitch.moddevgradle {
        val mixinExtrasNeoforge = "io.github.llamalad7:mixinextras-neoforge:0.5.0-beta.2"
        modstitchJiJ(mixinExtrasNeoforge)
        modstitchImplementation(mixinExtrasNeoforge)
    }

    val commonmarkDep = "org.commonmark:commonmark:0.21.0"
    modstitchImplementation(commonmarkDep)
    modstitchJiJ(commonmarkDep)

    modstitchModImplementation("dev.architectury:architectury-${loader}:${property("deps.arch_api")}")
    modstitchModImplementation("dev.imb11:mru:${property("deps.mru")}+${loader}")

    modstitchModCompileOnly("dev.emi:emi-${loader}:${property("compile.emi")}")

    modstitchModRuntimeOnly("me.shedaniel:RoughlyEnoughItems-${loader}:${property("runtime.rei")}")
    modstitchModCompileOnly("me.shedaniel:RoughlyEnoughItems-api:${property("runtime.rei")}")
    modstitchModCompileOnly("me.shedaniel:RoughlyEnoughItems-default-plugin:${property("runtime.rei")}")

    modstitchModCompileOnly("maven.modrinth:inventorio:${property("compile.inventorio")}")
    modstitchModCompileOnly("maven.modrinth:trashslot:${property("compile.trashslot")}")

    val yaclDep = "dev.isxander:yet-another-config-lib:${property("deps.yacl")}-${loader}"
    modstitchJiJ(yaclDep)
    modstitchModImplementation(yaclDep)
}

sourceSets {
    main {
        resources {
            srcDirs += files("src/main/generated")
        }
    }
}

tasks.processResources {
    // Make properties map mutable so that additional properties can be added.
    val props = mutableMapOf(
        "mod_version" to project.version,
        "target_minecraft" to project.property("mod.target"),
        "target_mru" to "${property("deps.mru")}+${loader}"
    )

    if (loader == "neoforge") {
        props["target_loader"] = project.property("fml.target")
        props["loader"] = loader
        props["mandatory_inclusion_field"] = "type = \"required\""
    }

    // Declare the properties as inputs on the task.
    inputs.properties(props)

    when (loader) {
        "fabric" -> {
            filesMatching("fabric.mod.json") { expand(props) }
            exclude("META-INF/neoforge.mods.toml")
        }
        "neoforge" -> {
            filesMatching("META-INF/neoforge.mods.toml") { expand(props) }
            exclude("fabric.mod.json")
        }
    }

    val packFormat = when (mcVersion) {
        "1.20.1" -> 15
        "1.21.1" -> 34
        "1.21.3" -> 42
        "1.21.4" -> 46
        else -> throw IllegalArgumentException("Unsupported Minecraft version: $mcVersion")
    }

    filesMatching("pack.mcmeta") {
        expand(mapOf("pack_format" to packFormat))
    }

    filesMatching("sounds.mixins.json") {
        expand(mapOf("version" to "$mcVersion-$loader"))
    }
}

tasks.jar {
    from("LICENSE") {
        rename { filename -> "${filename}_${project.base.archivesName.get()}" }
    }
}