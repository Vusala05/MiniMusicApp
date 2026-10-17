plugins {
    id("musicapp.android.library")
    id("musicapp.android.compose")
    id("musicapp.android.hilt")

}

android {
    namespace = "com.example.feature.detail.ui"
}

dependencies {

    implementation(libs.logging.interceptor)

    //Hilt
    implementation(libs.androidx.hilt.navigation.compose)

    //Media3
    implementation(libs.androidx.media3.ui.compose.material3)


    implementation(project(":core-data"))
    implementation(project(":core-ui"))
    implementation(project(":feature-detail:api"))
    implementation(project(":feature-home:api"))
    implementation(project(":feature-download:api"))
    implementation(project(":service"))
    implementation(project(":core-sync"))



}