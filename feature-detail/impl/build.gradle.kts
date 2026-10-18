plugins {
    id("musicapp.android.library")
    id("musicapp.android.hilt")
    id("musicapp.android.compose")
    alias(libs.plugins.kotlin.serialization)

}

android {
    namespace = "com.example.feature.detail.impl"
}

dependencies {

    implementation(libs.logging.interceptor)

    implementation(libs.androidx.hilt.navigation.compose)

    //Media3
    implementation(libs.androidx.media3.ui.compose.material3)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json.v1110)

    implementation(project(":core-domain"))
    implementation(project(":feature-detail:api"))
    implementation(project(":feature-home:api"))
    implementation(project(":core-ui"))
    implementation(project(":feature-download:api"))
    implementation(project(":service"))
    implementation(project(":core-sync"))

}