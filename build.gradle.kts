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

val productionMods: Configuration by configurations.creating {
    isTransitive = false
}

if (loader == "fabric") {
    @Suppress("UnstableApiUsage")
    val runProdClient by tasks.registering(net.fabricmc.loom.task.prod.ClientProductionRunTask::class) {
        group = "fabric"

        mods.from(productionMods)

        jvmArgs = listOf("-Dsodium.checks.issue2561=false")

        outputs.upToDateWhen { false }
    }
} else {
    val runProdClient by tasks.registering {
        group = "sounds/versioned"
        dependsOn("runClient") // neoforge is prod always
    }
}
createActiveTask(taskName = "runClient")
createActiveTask(taskName = "runProdClient")

modstitch {
    minecraftVersion = mcVersion
    javaTarget = if (stonecutter.eval(mcVersion, ">1.20.4")) 21 else 17

//    parchment {
//        enabled = true
//        minecraftVersion = mcVersion
//        mappingsVersion = when (mcVersion) {
//            "1.21.1" -> "2024.11.17"
//            "1.21.3" -> "2024.12.07"
//            "1.21.4" -> "2025.01.19"
//            else -> throw IllegalArgumentException("Unsupported Minecraft version: $mcVersion")
//        }
//    }

    metadata {
        modId = "sounds"
        modName = "Sounds"
        modVersion = "${property("mod.version") as String}+${mcVersion}+${loader}"
        modGroup = "dev.imb11"
        modDescription =
            "A highly configurable sound overhaul mod that adds new sound effects while improving vanilla sounds too."
        modLicense = "ARR"

        replacementProperties.put(
            "pack_format", when (mcVersion) {
                "1.20.1" -> 15
                "1.21.1" -> 34
                "1.21.3" -> 42
                "1.21.4" -> 46
                "1.21.5" -> 52
                else -> throw IllegalArgumentException("Unsupported Minecraft version: $mcVersion")
            }.toString()
        )
        replacementProperties.put("target_minecraft", mcVersion)
        replacementProperties.put("target_mru", property("deps.mru") as String)
        replacementProperties.put(
            "target_loader", when (loader) {
                "neoforge" -> property("deps.neoforge") as String
                else -> ""
            }
        )
        replacementProperties.put("loader", loader)
        replacementProperties.put("target_fabricloader", when (loader) {
            "fabric" -> property("deps.fabric_loader") as String
            else -> ""
        })

        replacementProperties.put("target_yacl", "${property("deps.yacl")}-${loader}");
    }

    loom {
        fabricLoaderVersion = property("deps.fabric_loader") as String

        configureLoom {
            runs {
                all {
                    runDir = "../../run"
                    ideConfigGenerated(true)
                }

                if (loader == "fabric" && stonecutter.eval(mcVersion, ">=1.21")) {
                    create("datagenClient") {
                        client()
                        name = "Data Generation Client"
                        vmArg("-Dfabric-api.datagen")
                        vmArg(
                            "-Dfabric-api.datagen.output-dir=" + project.rootDir.toPath().resolve("src/main/generated")
                        )
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
            runs {
                all {
                    gameDirectory = file("../../run")
                }
            }
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
    fun Dependency?.productionMod() = this?.also { productionMods(it) }

    modstitch.loom {
        modstitchModImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}").productionMod()
        modstitchModImplementation("com.terraformersmc:modmenu:${property("runtime.modmenu")}").productionMod()
        "io.github.llamalad7:mixinextras-fabric:0.5.0-rc.1".let {
            modstitchJiJ(it)
            modstitchImplementation(it)
            annotationProcessor(it)
        }
    }

    modstitch.moddevgradle {
        "io.github.llamalad7:mixinextras-neoforge:0.5.0-rc.1".let {
            modstitchJiJ(it)
            implementation(it)
        }
    }

    modstitchModImplementation("dev.imb11:mru:${property("deps.mru")}+${loader}").productionMod()

    modstitchModCompileOnly("dev.emi:emi-${loader}:${property("compile.emi")}")
    modstitchModCompileOnly("maven.modrinth:trashslot:${property("compile.trashslot")}")

    modstitchModImplementation("dev.isxander:yet-another-config-lib:${property("deps.yacl")}-${loader}").productionMod()
}

sourceSets {
    main {
        resources {
            srcDir(file("src/main/generated"))
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

fun createActiveTask(
    taskProvider: TaskProvider<*>? = null,
    taskName: String? = null,
    internal: Boolean = false
): String {
    val taskExists = taskProvider != null || taskName!! in tasks.names
    val task = taskProvider ?: taskName?.takeIf { taskExists }?.let { tasks.named(it) }
    val taskName = when {
        taskProvider != null -> taskProvider.name
        taskName != null -> taskName
        else -> error("Either taskProvider or taskName must be provided")
    }
    val activeTaskName = "${taskName}Active"

    if (stonecutter.current.isActive) {
        rootProject.tasks.register(activeTaskName) {
            group = "sounds${if (internal) "/versioned" else ""}"

            task?.let { dependsOn(it) }
        }
    }

    return activeTaskName
}

val buildAndCollect by tasks.registering(Copy::class) {
    group = "build"

    dependsOn(modstitch.finalJarTask)
    from(modstitch.finalJarTask.flatMap { it.archiveFile })

    into(rootProject.layout.buildDirectory.dir("finalJars"))
}
createActiveTask(buildAndCollect)