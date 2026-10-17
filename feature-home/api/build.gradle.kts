plugins {
    id("musicapp.android.library")
    id("musicapp.android.hilt")

}

android {
    namespace = "com.example.feature.home.api"

}

dependencies {

    implementation(libs.logging.interceptor)

    implementation(project(":core-data"))
    implementation(project(":service"))

    //Media3
    implementation(libs.androidx.media3.ui.compose.material3)

}