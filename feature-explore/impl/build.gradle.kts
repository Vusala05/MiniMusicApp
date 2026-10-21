plugins {
    id("musicapp.android.library")
    id("musicapp.android.compose")
    id("musicapp.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.feature.explore.impl"

}

dependencies {

    implementation(libs.logging.interceptor)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json.v1110)

    //Hilt Navigation
    implementation(libs.androidx.hilt.navigation.compose)

    //Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    implementation(project(":core-ui"))
    implementation(project(":core-domain"))
    implementation(project(":feature-explore:api"))
    implementation(project(":navigation"))
    implementation(project(":core-data"))
    implementation(project(":service"))//sonra silersen

}