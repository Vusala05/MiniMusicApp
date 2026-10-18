plugins {
    id("musicapp.android.library")
    id("musicapp.android.compose")
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
    implementation(project(":core-domain"))
    implementation(project(":storage"))
    implementation(project(":core-ui"))
    implementation(project(":feature-home:api"))
    implementation(project(":service"))
    implementation(project(":navigation"))



}