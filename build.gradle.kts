
plugins {
    //trick: for the same plugin versions in all sub-modules
    /*
    id("com.android.application").version("8.2.0-alpha10").apply(false)
    id("com.android.library").version("8.2.0-alpha10").apply(false)
    kotlin("android").version("1.8.21").apply(false)
    kotlin("multiplatform").version("1.8.21").apply(false)
    */
    /** Once ksp is support by dagger hilt */
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}


buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Deps.kotlinGradlePlugin)
        classpath(Deps.androidBuildTools)
        classpath(Deps.sqlDelightGradlePlugin)
        classpath(Deps.hiltGradlePlugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
