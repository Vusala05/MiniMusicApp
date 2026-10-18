plugins {
    id("musicapp.android.library")
    id("musicapp.android.hilt")

}

android {
    namespace = "com.example.feature.saved.api"

}

dependencies {

    implementation(libs.logging.interceptor)

    //Hilt
    implementation(libs.androidx.hilt.navigation.compose)


    implementation(project(":core-sync"))
    implementation(project(":service"))
    implementation(project(":storage"))



}