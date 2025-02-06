plugins {
    id("dev.isxander.modstitch.base") version "0.5.12"
}

val loader = when {
    modstitch.isLoom -> "fabric"
    modstitch.isModDevGradleRegular -> "neoforge"
    modstitch.isModDevGradleLegacy -> "forge"
    else -> throw IllegalStateException("Unsupported loader")
}
val mcVersion = property("deps.minecraft") as String

modstitch {
    minecraftVersion = mcVersion
    javaTarget = if (stonecutter.eval(mcVersion, ">1.20.4")) 21 else 17

    parchment {
        enabled = true
        minecraftVersion = mcVersion
        mappingsVersion = when (mcVersion) {
            "1.21.1" -> "2024.11.17"
            "1.21.3" -> "2024.12.07"
            "1.21.4" -> "2025.01.19"
            else -> throw IllegalArgumentException("Unsupported Minecraft version: $mcVersion")
        }
    }

    metadata {
        modId = "sounds"
        modName = "Sounds"
        modVersion = "${property("mod.version") as String}+${mcVersion}+${loader}"
        modGroup = "dev.imb11"
        modDescription = "A highly configurable sound overhaul mod that adds new sound effects while improving vanilla sounds too."
        modLicense = "ARR"

        replacementProperties.put("pack_format", when (mcVersion) {
            "1.20.1" -> 15
            "1.21.1" -> 34
            "1.21.3" -> 42
            "1.21.4" -> 46
            else -> throw IllegalArgumentException("Unsupported Minecraft version: $mcVersion")
        }.toString())
        replacementProperties.put("target_minecraft", mcVersion)
        replacementProperties.put("target_mru", property("deps.mru") as String)
        replacementProperties.put("target_loader", when (loader) {
            "neoforge" -> property("deps.neoforge") as String
            else -> ""
        })
        replacementProperties.put("loader", loader)
    }

    loom {
        fabricLoaderVersion = "+"

        configureLoom {
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
        enable {
            neoForgeVersion = findProperty("deps.neoforge") as String
        }

        defaultRuns()
        configureNeoforge {
            // empty configuration
        }
    }

//    mixin {
////        addMixinsToModManifest = true
//
////        configs.register("sounds")
////        if (loader == "fabric") configs.register("sounds-fabric")
////        if (loader == "neoforge") configs.register("sounds-neoforge")
//    }
}

stonecutter {
    consts(
        "fabric" to modstitch.isLoom,
        "neoforge" to modstitch.isModDevGradleRegular,
        "forgelike" to modstitch.isModDevGradle,
    )
}

dependencies {
    modstitch.loom {
        modstitchModImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")
        modstitchModImplementation("com.terraformersmc:modmenu:${property("runtime.modmenu")}")
        "io.github.llamalad7:mixinextras-fabric:0.5.0-beta.5".let {
            modstitchJiJ(it)
            implementation(it)
        }
    }

    modstitch.moddevgradle {
        "io.github.llamalad7:mixinextras-neoforge:0.5.0-beta.5".let {
            modstitchJiJ(it)
            implementation(it)
        }
    }

    val commonmarkDep = "org.commonmark:commonmark:0.21.0"
    modstitchImplementation(commonmarkDep)
    modstitchJiJ(commonmarkDep)

    modstitchModImplementation("dev.imb11:mru:${property("deps.mru")}+${loader}")

    modstitchModCompileOnly("dev.emi:emi-${loader}:${property("compile.emi")}")

    modstitchModRuntimeOnly("me.shedaniel:RoughlyEnoughItems-${loader}:${property("runtime.rei")}")
    modstitchModCompileOnly("me.shedaniel:RoughlyEnoughItems-api:${property("runtime.rei")}")
    modstitchModCompileOnly("me.shedaniel:RoughlyEnoughItems-default-plugin:${property("runtime.rei")}")

    modstitchModCompileOnly("maven.modrinth:inventorio:${property("compile.inventorio")}")
    modstitchModCompileOnly("maven.modrinth:trashslot:${property("compile.trashslot")}")

    "dev.isxander:yet-another-config-lib:${property("deps.yacl")}-${loader}".let {
        modstitchJiJ(it)
        modstitchModApi(it) {
            exclude(group = "net.fabricmc.fabric-api", module = "fabric-api")
            exclude(group = "thedarkcolour")
        }
    }
}

sourceSets {
    main {
        resources {
            srcDirs += files("src/main/generated")
        }
    }
}

// warning: if using shadow modstitch extension in the future,
// this will be ignored since jar is not used, use shadowJar instead
tasks.jar {
    from("LICENSE") {
        rename { filename -> "${filename}_${project.base.archivesName.get()}" }
    }
}
