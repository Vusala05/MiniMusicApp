plugins {
    id("musicapp.android.library")
    id("musicapp.android.compose")
    id("musicapp.android.hilt")
    alias(libs.plugins.kotlin.serialization)


}

android {
    namespace = "com.example.feature.home.impl"

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

    //Media3
    implementation(libs.androidx.media3.ui.compose.material3)


    implementation(project(":core-ui"))
    implementation(project(":core-domain"))
    implementation(project(":feature-home:api"))
    implementation(project(":navigation"))
    implementation(project(":service"))
    implementation(project(":core-data"))
}