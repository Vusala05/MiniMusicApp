plugins {
    id("musicapp.android.library")
    id("musicapp.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.storage"
}

dependencies {

    implementation(libs.logging.interceptor)

    //Database
    implementation(libs.androidx.room3.runtime)
    ksp(libs.androidx.room3.compiler)
}