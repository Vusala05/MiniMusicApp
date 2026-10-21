plugins {
    id("musicapp.android.library")
    id("musicapp.android.hilt")

}

android {
    namespace = "com.example.feature.home.api"

}

dependencies {

    implementation(libs.logging.interceptor)

    //Media3
    implementation(libs.androidx.media3.ui.compose.material3)

    implementation(project(":core-domain"))
    implementation(project(":service"))



}