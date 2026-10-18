plugins {
    id("musicapp.android.library")
    id("musicapp.android.compose")
    id("musicapp.android.hilt")

}

android {
    namespace = "com.example.feature.home.ui"

}

dependencies {

    implementation(libs.logging.interceptor)

    //Hilt
    implementation(libs.androidx.hilt.navigation.compose)

    //Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    //Media3
    implementation(libs.androidx.media3.ui.compose.material3)


    implementation(project(":core-ui"))
    implementation(project(":core-domain"))
    implementation(project(":feature-home:api"))
    implementation(project(":navigation"))
    implementation(project(":service"))


}