plugins {
    id("musicapp.android.library")
    id("musicapp.android.compose")
}

android {
    namespace = "com.example.core_ui"
}

dependencies {

    //Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)


    implementation(project(":core-domain"))
}