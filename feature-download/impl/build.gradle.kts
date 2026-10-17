plugins {
    id("musicapp.android.library")
    id("musicapp.android.hilt")

}

android {
    namespace = "com.example.feature.saved.impl"

}

dependencies {

    //Hilt Navigation
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.logging.interceptor)

    implementation(project(":feature-download:api"))
    implementation(project(":core-data"))
    implementation(project(":storage"))



}