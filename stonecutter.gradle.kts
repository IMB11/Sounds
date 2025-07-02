plugins {
    id("dev.kikugie.stonecutter")
}
stonecutter active "1.21.7-fabric" /* [SC] DO NOT EDIT */

stonecutter registerChiseled tasks.register("chiseledBuild", stonecutter.chiseled) { 
    group = "project"
    ofTask("build")
}

stonecutter registerChiseled tasks.register("chiseledBuildAndCollect", stonecutter.chiseled) {
    group = "project"
    ofTask("buildAndCollect")
}

allprojects {
    repositories {
        mavenCentral()
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
        maven("https://maven.isxander.dev/releases")
        maven("https://thedarkcolour.github.io/KotlinForForge/")
    }
}
