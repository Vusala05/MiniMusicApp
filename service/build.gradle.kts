plugins {
    id("musicapp.android.library")
    id("musicapp.android.hilt")

}

android {
    namespace = "com.example.service"

}

dependencies {

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    //ExoPlayer
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.session)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.ui.compose.material3)

    implementation(libs.logging.interceptor)

    implementation(project(":core-ui"))
    implementation(project(":core-domain"))
    implementation(project(":core-sync"))
    implementation(project(":navigation"))


}