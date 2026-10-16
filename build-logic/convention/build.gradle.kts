plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "musicapp.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
    }
}